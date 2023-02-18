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
     * 내 아이디를 저장하는 공간
     */
    private val myUserId = MutableStateFlow("-1")

    /**
     * 조회중인 유저 아이디 저장하는 공간
     */
    private val _currentUserId = MutableStateFlow("-9")
    val currentUserId : StateFlow<String> get() = _currentUserId

    /**
     * 유저 파도타기를 위한 히스토리 공간
     */
    private val userHistory = mutableListOf<String>()

    /**
     * 팔로워 & 팔로잉 리스트를 저장하는 공간
     */
    private val _followList = MutableStateFlow(mutableListOf<Follow>())
    val followList: StateFlow<MutableList<Follow>> get() = _followList

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

    /**
     * 내 작품 스크랩 리스트를 저장하는 공간
     */
    private val _productWishList = MutableStateFlow(mutableListOf<Product>())
    val productWishList : StateFlow<MutableList<Product>> get() = _productWishList

    /**
     * 내 의뢰 스크랩 리스트를 저장하는 공간
     */
    private val _worktWishList = MutableStateFlow(mutableListOf<Product>())
    val workWishList : StateFlow<MutableList<Product>> get() = _worktWishList

    /**
     * 팔로우,언팔로우 플래그
     */
    val followControlSuccessFlag = MutableStateFlow(false)

    /**
     * 내 작품, 프로필인가?
     */
    fun isMine(): Boolean {
        return myUserId.value == _currentUserId.value
    }
    fun isMine(userId : String): Boolean {
        return myUserId.value == userId
    }


    fun getProfile() {
        clearProfileContent()
        CallImpl(
            API_GET_MY_PROFILE,
            this,
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun getOtherProfile() {
        CallImpl(
            API_GET_USER_PROFILE,
            this,
            paramStr0 = _currentUserId.value
        ).apply {
            remote.sendRequestApi(this)
        }
    }


    fun getFollowList(page: Int) {
        _followList.value = mutableListOf()
        if (page == 0) {
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

    fun getWishList()
    {
        CallImpl(
            API_GET_MY_WISH_LIST,
            this
        ).apply {
            remote.sendRequestApi(this)
        }
    }

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

    private fun onFollowResponse(response: FollowResponse) {
        response.follows?.let { follows ->
            _followList.value = follows.toMutableList()
        }
    }

    private fun onProfileResponse(response: UserProfileResponse)
    {
        response.data?.let { profileResponse ->
            profile.value = profileResponse.profile
            _workList.value = profileResponse.works!!.toMutableList().asReversed()
            _productList.value = profileResponse.products!!.toMutableList().asReversed()
        }
    }
    private fun onMyProfileResponse(response: UserProfileResponse)
    {
        response.data?.let { profileResponse ->
            profile.value = profileResponse.profile
            profileResponse.profile.userId.let {
                myUserId.value = it
                _currentUserId.value = it
                prefs.setString(CODE_USER_ID, it)
            }
            prefs.setString(CODE_USER_NAME, profileResponse.profile.name)
            _workList.value = profileResponse.works!!.toMutableList().asReversed()
            _productList.value = profileResponse.products!!.toMutableList().asReversed()
            _workEmployList.value = profileResponse.workEmploys!!.toMutableList().asReversed()
        }
    }

    private fun onWishListResponse(response: WishResponse)
    {
        response.data?.let { wishResponse ->
            _productWishList.value = wishResponse.productWishes!!.toMutableList()
            _worktWishList.value = wishResponse.workWishes!!.toMutableList()
        }
    }

    fun changeProfile(newUserId: String)
    {
        clearProfileContent()
        if (_currentUserId.value != newUserId)
        {
            userHistory.add(_currentUserId.value)
            _currentUserId.value = newUserId
        }

    }


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

    private fun clearUserHistory()
    {
        userHistory.clear()
    }

    private fun clearProfileContent()
    {
        profile.value = null
        _workList.value = mutableListOf()
        _productList.value = mutableListOf()
        _workEmployList.value = mutableListOf()
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
                    globalUi.showToast("팔로잉합니다")
                }
                else {
                    globalUi.showToast(body.message.toString())
                }
            }

            API_UNFOLLOW_USER ->
            {
                if (body.success == true)
                {
                    followControlSuccessFlag.value = true
                    globalUi.showToast("언팔로잉합니다")
                }
                else {
                    globalUi.showToast(body.message.toString())
                }
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }
}