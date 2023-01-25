package io.sinzak.android.model.profile

import io.sinzak.android.constants.*
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.profile.Follow
import io.sinzak.android.remote.dataclass.request.profile.FollowRequest
import io.sinzak.android.remote.dataclass.response.profile.FollowResponse
import io.sinzak.android.remote.dataclass.response.profile.UserProfileResponse
import io.sinzak.android.remote.retrofit.CallImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileModel @Inject constructor() : BaseModel() {

    /**
     * 현재 조회중인 프로필을 저장하는 공간
     */
    val profile = MutableStateFlow<UserProfileResponse?>(null)

    /**
     * 내 아이디를 저장하는 공간
     */
    val myUserId = MutableStateFlow("-1")

    /**
     * 조회중의 타인 유저 아이디 저장하는 공간
     */
    val currenUserId = MutableStateFlow("-1")
    /**
     * 팔로워 리스트를 저장하는 공간
     */
    private val _followerList = MutableStateFlow(mutableListOf<Follow>())
    val followerList : StateFlow<List<Follow>> get() = _followerList

    /**
     * 팔로잉 리스트를 저장하는 공간
     */
    private val _followingList = MutableStateFlow(mutableListOf<Follow>())
    val followingList : StateFlow<List<Follow>> get() = _followingList


/*    fun getUserId() : String?{
        profile.value?.let{
            return it.userId
        }
        return null
    }

    fun getUserDisplayName() : String?{
        profile.value?.let{
            return it.name
        }
        return null
    }*/

    /**
     * 내 작품, 프로필인가?
     */
    fun isMine() : Boolean {
        return myUserId.value == currenUserId.value
    }

    /**
     * 조회 중인 작가 아이디 설정
     */
    fun setCurrentUserId(userId: String) {
        currenUserId.value = userId
    }

    fun getProfile()
    {
        profile.value = null
        CallImpl(
            API_GET_MY_PROFILE,
            this
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun getOtherProfile()
    {
        profile.value = null
        CallImpl(
            API_GET_USER_PROFILE,
            this,
            paramStr0 = currenUserId.value
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun getFollowerList(userId : String)
    {
        _followerList.value = mutableListOf()
        CallImpl(
            API_GET_FOLLOWER_LIST,
            this,
            paramStr0 = userId
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun getFollowingList(userId : String)
    {
        _followingList.value = mutableListOf()
        CallImpl(
            API_GET_FOLLOWING_LIST,
            this,
            paramStr0 = userId
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun followUser(userId: String , isFollow : Boolean) {
        val request = FollowRequest(userId)

        if (isFollow){
            CallImpl(
                API_UNFOLLOW_USER,
                this,
                request
            ).apply {
                remote.sendRequestApi(this)
            }
        }
        else {
            CallImpl(
                API_FOLLOW_USER,
                this,
                request
            ).apply {
                remote.sendRequestApi(this)
            }
        }

    }

    private fun onFollowListResponse(response: FollowResponse, stateFlow: MutableStateFlow<MutableList<Follow>>)
    {
        response.followList.let{ follow ->
            val list = mutableListOf<Follow>()
            list.addAll(stateFlow.value)
            list.addAll(follow)
        }
    }



    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api)
        {
            API_GET_USER_PROFILE -> {
                if (body is UserProfileResponse) {
                    profile.value = body
                }
            }

            API_GET_FOLLOWER_LIST -> {
                onFollowListResponse(body as FollowResponse, _followerList)
            }

            API_GET_FOLLOWING_LIST -> {
                onFollowListResponse(body as FollowResponse, _followingList)
            }
            API_GET_MY_PROFILE -> {
                if (body is UserProfileResponse) {
                    myUserId.value = body.userId
                    profile.value = body
                }
            }

            API_FOLLOW_USER ->
            {
                if (body.success == true)
                {
                    globalUi.showToast("팔로잉합니다")
                }
            }

            API_UNFOLLOW_USER ->
            {
                if (body.success == true)
                {
                    globalUi.showToast("언팔로잉합니다")
                }
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }
}