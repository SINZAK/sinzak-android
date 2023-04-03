package io.sinzak.android.model.profile

import io.sinzak.android.constants.*
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.remote.dataclass.profile.*
import io.sinzak.android.remote.dataclass.request.profile.FollowRequest
import io.sinzak.android.remote.dataclass.response.profile.FollowResponse
import io.sinzak.android.remote.dataclass.response.profile.UserProfileResponse
import io.sinzak.android.remote.dataclass.response.profile.WishResponse
import io.sinzak.android.remote.retrofit.CallImpl
import io.sinzak.android.system.App.Companion.prefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileModel @Inject constructor() : BaseModel() {


    /**
     * 현재 조회중인 프로필을 저장하는 공간
     */
    val profile = MutableStateFlow<UserProfile?>(null)

    /**
     * 최초 로그인 확인
     */
    val isFirstLogin = MutableStateFlow(false)

    /**
     * 내 아이디를 저장하는 공간
     */
    private val myUserId = MutableStateFlow(INIT_MY_ID)

    var categoryLike = ""
    fun changeCategory(changeCategory : String){
        categoryLike = changeCategory
    }

    /**
     * 조회중인 유저 아이디 저장하는 공간
     */
    private val _currentUserId = MutableStateFlow(INIT_CURRENT_ID)
    val currentUserId : StateFlow<String> get() = _currentUserId

    /**
     * 유저 파도타기를 위한 히스토리 공간
     */
    private val userHistory = mutableListOf<String>()

    /**
     * 조회중인 유저 작업해요 리스트를 저장하는 공간
     */
    private val _workList = MutableStateFlow(mutableListOf<Product>())
    val workList: StateFlow<MutableList<Product>> get() = _workList

    /**
     * 조회중인 유저 판매 작품 리스트를 저장하는 공간
     */
    private val _productList = MutableStateFlow(mutableListOf<Product>())
    val productList: StateFlow<MutableList<Product>> get() = _productList

    /**
     * 조회중인 유저 의뢰해요 리스트를 저장하는 공간
     */
    private val _workEmployList = MutableStateFlow(mutableListOf<Product>())
    val workEmployList : StateFlow<MutableList<Product>> get() = _workEmployList



    /********************************
     * 프로필 데이터 초기화
     ********************************/

    private fun clearProfileContent()
    {
        profile.value = null
        _workList.value = mutableListOf()
        _productList.value = mutableListOf()
        _workEmployList.value = mutableListOf()
    }


    /********************************
     * 내 프로필 보기 플로우
     ********************************/

    /**
     * 1. ProfileFragment가 생성될 때, getProfile을 실행함
     * 2. 기존 프로필 데이터를 초기화
     * 3. 서버에 내 프로필 데이터를 요청
     */
    fun getProfile() {
        clearProfileContent()
        CallImpl(
            API_GET_MY_PROFILE,
            this,
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    /**
     * 4. 서버에 요청 성공시 받은 데이터를 모델에 저장
     * 5. currentUserId를 내 id로 저장
     */
    private fun onMyProfileResponse(response: UserProfileResponse)
    {

        response.data?.let { profileResponse ->

            profileResponse.profile.let {
                profile.value = it
                setMyId(it.userId)
                prefs.setString(CODE_USER_NAME, it.name)
                categoryLike = it.categoryLike.toString()
            }

            _workList.value = profileResponse.works!!.toMutableList().asReversed()
            _productList.value = profileResponse.products!!.toMutableList().asReversed()
            _workEmployList.value = profileResponse.workEmploys!!.toMutableList().asReversed()
        }
    }

    private fun setMyId(userId: String)
    {
        myUserId.value = userId
        prefs.setString(CODE_USER_ID, userId)
        if (_currentUserId.value != userId)
        {
            userHistory.add(userId)
            _currentUserId.value = userId
        }
    }

    /********************************
     * 타인 프로필 보기 플로우 (내 프로필이어도 적용)
     ********************************/

    /**
     * 1. OtherFragment 생성 전, Product의 userId 혹은 FollowList의 userId를 currentUserId와 비교
     * 2. 다른 아이디라면, 프로필 데이터를 초기화 + 히스토리에 현재 아이디를 저장 + 새 아이디를 currentUserId에 배정
     * 3. 같은 아이디라면, 넘어감
     */
    fun changeProfile(newUserId: String)
    {
        if (_currentUserId.value != newUserId)
        {
            clearProfileContent()
            userHistory.add(_currentUserId.value)
            _currentUserId.value = newUserId
        }

    }

    /**
     * 4. OtherFragment 생성 시, 서버에 프로필 데이터를 요청함
     */
    fun getOtherProfile() {
        CallImpl(
            API_GET_USER_PROFILE,
            this,
            paramStr0 = _currentUserId.value
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    /**
     * 5. 서버에 요청 성공시 받은 데이터를 모델에 저장
     */
    private fun onProfileResponse(response: UserProfileResponse)
    {
        response.data?.let { profileResponse ->
            profile.value = profileResponse.profile
            _workList.value = profileResponse.works!!.toMutableList().asReversed()
            _productList.value = profileResponse.products!!.toMutableList().asReversed()
        }
    }

    /**
     * 6. 뒤로가기 시, 히스토리에 하나 이상 있는지 확인
     * 7. 하나 이상일 시, 마지막 유저 아이디를 현재 아이디로 배정 + 히스토리 마지막을 제거 + 배정한 현재 아이디로 (4)번 실행
     * 8. 마지막 히스토리일 시, 넘어감
     */
    fun revealProfileHistory() : Boolean {
        if (userHistory.size > 1)
        {
            _currentUserId.value = userHistory.last()
            userHistory.removeLast()
            getOtherProfile()
            return true
        }
        return false
    }


    /********************************
     * 공통 프로필 보기 플로우
     ********************************/

    /**
     * 팔로우,언팔로우 플래그
     */
    val followControlSuccessFlag = MutableStateFlow(false)

    /**
     * 팔로우 하기
     * 1. 팔로우중이면, 언팔로우 요청
     * 2. 팔로우중 아니면, 팔로우 요청
     */
    fun followUser(isFollow: Boolean, userId: String) {

        val request = FollowRequest(userId)
        if (isFollow) {
            CallImpl(
                API_UNFOLLOW_USER,
                this,
                request
            ).apply {
                remote.sendRequestApi(this)
            }
        } else {
            CallImpl(
                API_FOLLOW_USER,
                this,
                request
            ).apply {
                remote.sendRequestApi(this)
            }
        }

    }

    /**
     * 팔로워 & 팔로잉 리스트를 저장하는 공간
     */
    private val _followList = MutableStateFlow(mutableListOf<Follow>())
    val followList: StateFlow<MutableList<Follow>> get() = _followList

    val followPageTap = MutableStateFlow(FOLLOWER_TAP)

    /**
     * 팔로우 리스트 보기
     * 1. FollowFragment 생성시, 모델의 followList를 초기화
     * 2. 팔로워, 팔로잉 선택에 따라 서버에 리스트를 요청
     */
    fun getFollowList(showFollower : Boolean) {
        _followList.value = mutableListOf()
        followPageTap.value = showFollower
        if (showFollower) {
            CallImpl(
                API_GET_FOLLOWER_LIST,
                this,
                paramStr0 = _currentUserId.value
            ).apply {
                remote.sendRequestApi(this)
            }
        } else {
            CallImpl(
                API_GET_FOLLOWING_LIST,
                this,
                paramStr0 = _currentUserId.value
            ).apply {
                remote.sendRequestApi(this)
            }
        }
    }

    /**
     * 3. 서버에 요청 성공시 모델의 followList에 데이터 배정
     */
    private fun onFollowResponse(response: FollowResponse) {
        response.follows?.let { follows ->
            _followList.value = follows.toMutableList()
        }
    }



    /**
     * 내 작품 스크랩 리스트를 저장하는 공간
     */
    private val _productWishList = MutableStateFlow(mutableListOf<Product>())
    val productWishList : StateFlow<MutableList<Product>> get() = _productWishList

    /**
     * 내 의뢰 스크랩 리스트를 저장하는 공간
     */
    private val _workWishList = MutableStateFlow(mutableListOf<Product>())
    val workWishList : StateFlow<MutableList<Product>> get() = _workWishList


    fun getWishList()
    {
        CallImpl(
            API_GET_MY_WISH_LIST,
            this
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    private fun onWishListResponse(response: WishResponse)
    {
        response.data?.let { wishResponse ->
            _productWishList.value = wishResponse.productWishes!!.toMutableList().asReversed()
            _workWishList.value = wishResponse.workWishes!!.toMutableList().asReversed()
        }
    }


    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api)
        {
            API_GET_USER_PROFILE -> {
                onProfileResponse(body as UserProfileResponse)
            }

            API_GET_FOLLOWER_LIST -> {
                onFollowResponse(body as FollowResponse)
            }

            API_GET_FOLLOWING_LIST -> {
                onFollowResponse(body as FollowResponse)
            }
            API_GET_MY_PROFILE -> {
                onMyProfileResponse(body as UserProfileResponse)
            }

            API_GET_MY_WISH_LIST -> {
                onWishListResponse(body as WishResponse)
            }

            API_FOLLOW_USER ->
            {
                if (body.success == true)
                {
                    followControlSuccessFlag.value = true
                }
            }

            API_UNFOLLOW_USER ->
            {
                if (body.success == true)
                {
                    followControlSuccessFlag.value = true
                }

            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {
        when(api)
        {
            API_FOLLOW_USER,
            API_UNFOLLOW_USER -> {
                followControlSuccessFlag.value = false
            }
        }
    }

    companion object {
        const val INIT_MY_ID = "-1"
        const val INIT_CURRENT_ID = "-9"
    }
}