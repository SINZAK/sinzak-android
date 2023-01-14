package io.sinzak.android.model.profile

import io.sinzak.android.constants.*
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.profile.FollowRequest
import io.sinzak.android.remote.dataclass.response.profile.FollowResponse
import io.sinzak.android.remote.dataclass.response.profile.UserProfileResponse
import io.sinzak.android.remote.retrofit.CallImpl
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileModel @Inject constructor() : BaseModel() {

    /**
     * 내 프로필을 저장하는 공간
     */
    val profile = MutableStateFlow<UserProfileResponse?>(null)

    val followList = MutableStateFlow<FollowResponse?>(null)

    fun getUserId() : String?{
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
    }




    fun getMyProfile()
    {
        profile.value = null
        CallImpl(
            API_GET_MY_PROFILE,
            this
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun getUserProfile(userId : String)
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

    fun getFollowerList(userId : String)
    {
        followList.value = null
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
        followList.value = null
        CallImpl(
            API_GET_FOLLOWING_LIST,
            this,
            paramStr0 = userId
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun followUser(userId: String) {
        val request = FollowRequest(userId)
        CallImpl(
            API_FOLLOW_USER,
            this,
            request
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun unfollowUser(userId: String) {
        val request = FollowRequest(userId)
        CallImpl(
            API_UNFOLLOW_USER,
            this,
            request
        ).apply {
            remote.sendRequestApi(this)
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
                if (body is FollowResponse){
                    followList.value = body
                }
            }
            API_GET_FOLLOWING_LIST -> {
                if (body is FollowResponse){
                    followList.value = body
                }
            }
            API_GET_MY_PROFILE -> {
                if (body is UserProfileResponse) {
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