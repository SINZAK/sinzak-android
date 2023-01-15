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

    /**
     * 타인 프로필을 저장하는 공간
     */
    val otherProfile = MutableStateFlow<UserProfileResponse?>(null)

    val followList = MutableStateFlow<FollowResponse?>(null)

    /**
     * 프로필 조회를 위해 userId를 저장하는 공간
     */
    val currentUserId = MutableStateFlow(0)

    fun getUserId() : String?{
        profile.value?.let{
            return it.userId.toString()
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

    fun getUserProfile()
    {
        otherProfile.value = null
        CallImpl(
            API_GET_USER_PROFILE,
            this,
            paramInt0 = currentUserId.value
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun getFollowerList(userId : Int)
    {
        followList.value = null
        CallImpl(
            API_GET_FOLLOWER_LIST,
            this,
            paramInt0 = userId
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun getFollowingList(userId : Int)
    {
        followList.value = null
        CallImpl(
            API_GET_FOLLOWING_LIST,
            this,
            paramInt0 = userId
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun followUser(userId: Int , isFollow : Boolean) {
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



    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api)
        {
            API_GET_USER_PROFILE -> {
                if (body is UserProfileResponse) {
                    otherProfile.value = body
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
                    currentUserId.value = body.userId
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