package io.sinzak.android.remote.retrofit

import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.profile.FollowRequest
import io.sinzak.android.remote.dataclass.request.profile.ReportRequest
import io.sinzak.android.remote.dataclass.request.profile.UpdateUserRequest
import io.sinzak.android.remote.dataclass.response.profile.FollowResponse
import io.sinzak.android.remote.dataclass.response.profile.UserProfileResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.Path

interface RemoteProfileInterface {
    @GET("api/users/{userId}/profile")
    fun getUserProfile(@HeaderMap header: HashMap<String,String>, @Path("userId") userId : String) : Call<UserProfileResponse>

    @GET("api/users/{userId}/followers")
    fun getFollowerList(@HeaderMap header: HashMap<String,String>, @Path("userId") userId : String) : Call<FollowResponse>

    @GET("api/users/{userId}/followings")
    fun getFollowingList(@HeaderMap header: HashMap<String,String>, @Path("userId") userId : String) : Call<FollowResponse>

    @GET("api/users/my-profile")
    fun getMyProfile(@HeaderMap header: HashMap<String,String>) : Call<UserProfileResponse>

    @POST("api/users/edit")
    fun editMyProfile(@HeaderMap header: HashMap<String, String>, @Body body: UpdateUserRequest) : Call<CResponse>

    @POST("api/users/follow")
    fun followUser(@HeaderMap header: HashMap<String, String>, @Body body: FollowRequest) : Call<CResponse>

    @POST("api/users/unfollow")
    fun unfollowUser(@HeaderMap header: HashMap<String, String>, @Body body: FollowRequest) : Call<CResponse>

    @POST("api/users/report")
    fun reportUser(@HeaderMap header: HashMap<String, String>, @Body body : ReportRequest) : Call<CResponse>
}