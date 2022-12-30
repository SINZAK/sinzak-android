package io.sinzak.android.remote.retrofit

import io.sinzak.android.constants.ACCESS_TOKEN
import io.sinzak.android.constants.API_LOGIN_EMAIL
import io.sinzak.android.constants.REFRESH_TOKEN
import io.sinzak.android.remote.dataclass.CRequest
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.login.LoginEmailBody
import io.sinzak.android.system.App.Companion.prefs
import retrofit2.Call

class CallImpl(
    val apiNum : Int,
    val callback : RemoteListener,
    val requestBody : CRequest? = null,
    val paramInt0 : Int? = null,
    val paramInt1 : Int? = null,
    val paramStr0 : String? = null
) {

    private val header : HashMap<String,String> get() =
        HashMap<String,String>().apply{
            this[ACCESS_TOKEN] = prefs.accessToken
            this[REFRESH_TOKEN] = prefs.refreshToken
        }


    fun getCall(remoteApi : RemoteInterface) : Call<CResponse>
    {
        return when(apiNum)
        {
            API_LOGIN_EMAIL -> remoteApi.loginEmail(header,requestBody as LoginEmailBody)


            else -> throw NoSuchMethodException()
        } as Call<CResponse>
    }


}