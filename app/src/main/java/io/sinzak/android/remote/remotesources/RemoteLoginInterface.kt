package io.sinzak.android.remote.remotesources

import com.google.gson.JsonObject
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.login.JoinRequest
import io.sinzak.android.remote.dataclass.request.login.LoginEmailBody
import io.sinzak.android.remote.dataclass.response.login.*
import retrofit2.Call
import retrofit2.http.*

interface RemoteLoginInterface {

    @POST("api/login")
    fun loginEmail(@HeaderMap header : HashMap<String, String>, @Body body : LoginEmailBody) : Call<LoginEmailResponse>


    @GET("https://openapi.naver.com/v1/nid/me")
    fun loginGetNaverEmail(@Header("Authorization") auth : String) : Call<NaverProfile>

    @POST("api/join")
    fun joinAccount(@HeaderMap header : HashMap<String,String>, @Body body : JoinRequest) : Call<JoinResponse>

    @POST("api/oauth/get")
    fun postAccessToken(@HeaderMap header : HashMap<String,String>, @Body body : JsonObject) : Call<OAuthGetResponse>
}