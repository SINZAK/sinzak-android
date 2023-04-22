package io.sinzak.android.remote.remotesources

import com.google.gson.JsonObject
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.login.GoogleRequest
import io.sinzak.android.remote.dataclass.request.login.JoinRequest
import io.sinzak.android.remote.dataclass.request.login.LoginEmailBody
import io.sinzak.android.remote.dataclass.request.login.OAuthRequest
import io.sinzak.android.remote.dataclass.response.login.*
import retrofit2.Call
import retrofit2.http.*

interface RemoteLoginInterface {

    @POST("api/login")
    fun loginEmail(@HeaderMap header : HashMap<String, String>, @Body body : LoginEmailBody) : Call<LoginEmailResponse>

    @POST("api/check/email")
    fun checkEmail(@HeaderMap header: HashMap<String, String>, @Body jsonObject: JsonObject): Call<CResponse>

    @POST("api/check/nickname")
    fun checkName(@HeaderMap header: HashMap<String, String>, @Body jsonObject: JsonObject) : Call<CResponse>

    @GET("https://openapi.naver.com/v1/nid/me")
    fun loginGetNaverEmail(@Header("Authorization") auth : String) : Call<NaverProfile>

    @POST("api/join")
    fun joinAccount(@HeaderMap header : HashMap<String,String>, @Body body : JoinRequest) : Call<JoinResponse>

    @POST("api/oauth/get")
    fun postAccessToken(@HeaderMap header : HashMap<String,String>, @Body body : JsonObject) : Call<OAuthGetResponse>

    @POST("api/oauth/get/google")
    fun postGoogleAccessToken(@HeaderMap header: HashMap<String, String>, @Body body : OAuthRequest) : Call<OAuthGetResponse>

    @POST("https://www.googleapis.com/oauth2/v4/token")
    fun getGoogleAccessToken(@Body body: GoogleRequest) : Call<GoogleResponse>

    @POST("api/aos/version")
    fun checkStoreVersion(@HeaderMap header: HashMap<String, String>) : Call<VersionResponse>
}