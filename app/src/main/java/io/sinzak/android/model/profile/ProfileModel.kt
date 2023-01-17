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
     * 조회중인 유저 아이디 저장하는 공간
     */
    val currenUserId = MutableStateFlow("-1")
    /**
     * 팔로워 & 팔로잉 리스트를 저장하는 공간
     */
    private val _followList = MutableStateFlow(mutableListOf<Follow>())
    val followList : StateFlow<List<Follow>> get() = _followList


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

    fun getCurrentName() : String?{
        profile.value?.let {
            return it.name
        }
        return null
    }

    /**
     * 내 작품, 프로필인가?
     */
    fun isMine() : Boolean {
        return myUserId.value.equals(currenUserId.value)
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

    fun getOtherProfile(userId: String)
    {
        profile.value = null
        CallImpl(
            API_GET_USER_PROFILE,
            this,
            paramStr0 = userId
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun getFollowList(userId : String, page : Int)
    {
        _followList.value = mutableListOf()

        if (page == 0) {
            CallImpl(
                API_GET_FOLLOWER_LIST,
                this,
                paramStr0 = userId
            ).apply {
                remote.sendRequestApi(this)
            }
        }

        else {
            CallImpl(
                API_GET_FOLLOWING_LIST,
                this,
                paramStr0 = userId
            ).apply {
                remote.sendRequestApi(this)
            }
        }

    }


    fun followUser(isFollow : Boolean) {
        val request = FollowRequest(currenUserId.value)

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

    private fun onFollowResponse(response: FollowResponse)
    {
        response.follows?.let { follows ->
            _followList.value = follows.toMutableList()
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
                onFollowResponse(body as FollowResponse)
            }

            API_GET_FOLLOWING_LIST -> {
                onFollowResponse(body as FollowResponse)
            }
            API_GET_MY_PROFILE -> {
                if (body is UserProfileResponse) {
                    body.userId.let {
                        myUserId.value = it
                        currenUserId.value = it
                    }
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