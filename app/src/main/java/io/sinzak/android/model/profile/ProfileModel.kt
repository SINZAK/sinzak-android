package io.sinzak.android.model.profile

import io.sinzak.android.constants.API_GET_FOLLOWER_LIST
import io.sinzak.android.constants.API_GET_FOLLOWING_LIST
import io.sinzak.android.constants.API_GET_MY_PROFILE
import io.sinzak.android.constants.API_GET_USER_PROFILE
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.response.profile.FollowResponse
import io.sinzak.android.remote.dataclass.response.profile.UserProfileResponse
import io.sinzak.android.remote.retrofit.CallImpl
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileModel @Inject constructor() : BaseModel() {

    val profile = MutableStateFlow<UserProfileResponse?>(null)

    val followList = MutableStateFlow<FollowResponse?>(null)


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
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }
}