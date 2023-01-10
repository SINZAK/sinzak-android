package io.sinzak.android.remote.retrofit

import io.sinzak.android.remote.dataclass.response.profile.FollowResponse
import io.sinzak.android.remote.dataclass.response.profile.UserProfileResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path

interface RemoteProfileInterface {
    @GET("/users/{userId}")
    fun getUserProfile(@HeaderMap header: HashMap<String,String>, @Path("userId") userId : String) : Call<UserProfileResponse>

    @GET("/users/{userId}/followers")
    fun getFollowerList(@HeaderMap header: HashMap<String,String>, @Path("userId") userId : String) : Call<FollowResponse>

    @GET("/users/{userId}/followings")
    fun getFollowingList(@HeaderMap header: HashMap<String,String>, @Path("userId") userId : String) : Call<FollowResponse>

    @GET("/users/myProfile")
    fun getMyProfile(@HeaderMap header: HashMap<String,String>) : Call<UserProfileResponse>
}