package io.sinzak.android.remote.remotesources

import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.profile.FollowRequest
import io.sinzak.android.remote.dataclass.request.profile.HistoryRequest
import io.sinzak.android.remote.dataclass.request.profile.ReportRequest
import io.sinzak.android.remote.dataclass.request.profile.UpdateUserRequest
import io.sinzak.android.remote.dataclass.response.history.HistoryResponse
import io.sinzak.android.remote.dataclass.response.profile.FollowResponse
import io.sinzak.android.remote.dataclass.response.profile.UserProfileResponse
import io.sinzak.android.remote.dataclass.response.profile.WishResponse
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

    @GET("api/users/wish")
    fun getWishList(@HeaderMap header: HashMap<String, String>) : Call<WishResponse>

    @POST("api/users/follow")
    fun followUser(@HeaderMap header: HashMap<String, String>, @Body body: FollowRequest) : Call<CResponse>

    @POST("api/users/unfollow")
    fun unfollowUser(@HeaderMap header: HashMap<String, String>, @Body body: FollowRequest) : Call<CResponse>

    @POST("api/users/report")
    fun reportUser(@HeaderMap header: HashMap<String, String>, @Body body : ReportRequest) : Call<CResponse>

    @GET("api/users/history")
    fun getSearchHistory(@HeaderMap header: HashMap<String, String>) : Call<HistoryResponse>

    @POST("api/users/history")
    fun deleteSearchHistory(@HeaderMap header: HashMap<String, String>,@Body body : HistoryRequest) : Call<CResponse>

    @POST("api/users/deletehistories")
    fun deleteAllSearchHistory(@HeaderMap header: HashMap<String, String>) : Call<CResponse>
}