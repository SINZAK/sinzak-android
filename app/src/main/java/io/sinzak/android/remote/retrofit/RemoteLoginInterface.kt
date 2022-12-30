package io.sinzak.android.remote.retrofit

import io.sinzak.android.remote.dataclass.request.login.LoginEmailBody
import io.sinzak.android.remote.dataclass.response.login.LoginEmailResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface RemoteLoginInterface {

    @POST("/login")
    fun loginEmail(@HeaderMap header : HashMap<String, String>, @Body body : LoginEmailBody) : Call<LoginEmailResponse>

}