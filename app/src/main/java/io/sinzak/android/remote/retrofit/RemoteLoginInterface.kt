package io.sinzak.android.remote.retrofit

import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.login.JoinRequest
import io.sinzak.android.remote.dataclass.request.login.LoginEmailBody
import io.sinzak.android.remote.dataclass.response.login.JoinResponse
import io.sinzak.android.remote.dataclass.response.login.LoginEmailResponse
import io.sinzak.android.remote.dataclass.response.login.NaverProfile
import io.sinzak.android.remote.dataclass.response.login.Token
import retrofit2.Call
import retrofit2.http.*

interface RemoteLoginInterface {

    @POST("/login")
    fun loginEmail(@HeaderMap header : HashMap<String, String>, @Body body : LoginEmailBody) : Call<LoginEmailResponse>


    @GET("https://openapi.naver.com/v1/nid/me")
    fun loginGetNaverEmail(@Header("Authorization") auth : String) : Call<NaverProfile>

    @POST("/join")
    fun joinAccount(@HeaderMap header : HashMap<String,String>, @Body body : JoinRequest) : Call<JoinResponse>
}