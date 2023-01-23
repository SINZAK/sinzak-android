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
    private val myUserId = MutableStateFlow("-1")

    /**
     * 조회중인 유저 아이디 저장하는 공간
     */
    private val _currentUserId = MutableStateFlow("-1")
    val currentUserId : StateFlow<String> get() = _currentUserId

    /**
     * 유저 파도타기를 위한 히스토리 공간
     */
    private val userHistory = mutableListOf<String>()

    /**
     * 팔로워 & 팔로잉 리스트를 저장하는 공간
     */
    private val _followList = MutableStateFlow(mutableListOf<Follow>())
    val followList : StateFlow<List<Follow>> get() = _followList


    /**
     * 내 작품, 프로필인가?
     */
    fun isMine() : Boolean {
        return myUserId.value == _currentUserId.value
    }


    fun getProfile()
    {
        profile.value = null
        CallImpl(
            API_GET_MY_PROFILE,
            this,
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    private fun getOtherProfile()
    {
        profile.value = null
        CallImpl(
            API_GET_USER_PROFILE,
            this,
            paramStr0 = _currentUserId.value
        ).apply {
            remote.sendRequestApi(this)
        }
    }


    fun getFollowList(page : Int)
    {
        _followList.value = mutableListOf()

        if (page == 0) {
            CallImpl(
                API_GET_FOLLOWER_LIST,
                this,
                paramStr0 = _currentUserId.value
            ).apply {
                remote.sendRequestApi(this)
            }
        }

        else {
            CallImpl(
                API_GET_FOLLOWING_LIST,
                this,
                paramStr0 = _currentUserId.value
            ).apply {
                remote.sendRequestApi(this)
            }
        }

    }


    fun followUser(isFollow : Boolean) {
        val request = FollowRequest(_currentUserId.value)

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

    fun changeProfile(userId: String)
    {
        if (_currentUserId.value != userId)
        {
            userHistory.add(_currentUserId.value)
            _currentUserId.value = userId
            getOtherProfile()
        }
    }


    fun revealProfileHistory() : Boolean {
        if (userHistory.size > 1)
        {
            _currentUserId.value = userHistory.last()
            userHistory.removeLast()
            return true
        }
        return false
    }

    fun clearUserHistory()
    {
        userHistory.clear()
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
                        _currentUserId.value = it
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