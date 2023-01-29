package io.sinzak.android.remote.remotesources

import io.sinzak.android.remote.dataclass.request.login.TokenRequest
import io.sinzak.android.remote.dataclass.response.login.Token
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface RemoteInterface :
    RemoteLoginInterface,
    RemoteMarketInterface,
    RemoteProfileInterface,
    RemoteCertifyInterface,
    RemoteChatInterface {

    @POST("api/reissue")
    fun refreshToken(
        @HeaderMap headerMap: HashMap<String, String>,
        @Body token: TokenRequest
    ): Call<Token>

}