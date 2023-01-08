package io.sinzak.android.remote.retrofit

import io.sinzak.android.constants.*
import io.sinzak.android.remote.dataclass.CRequest
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.login.JoinRequest
import io.sinzak.android.remote.dataclass.request.login.LoginEmailBody
import io.sinzak.android.remote.dataclass.request.login.TokenRequest
import io.sinzak.android.remote.dataclass.request.market.ProductBuildRequest
import io.sinzak.android.system.App.Companion.prefs
import okhttp3.MultipartBody
import retrofit2.Call

class CallImpl(
    val apiNum : Int,
    val callback : RemoteListener,
    val requestBody : CRequest? = null,
    val paramInt0 : Int? = null,
    val paramInt1 : Int? = null,
    val paramStr0 : String? = null,
    val paramStr1 : String? = null,
    val multipartList : List<MultipartBody.Part>? = null
) {

    private val header : HashMap<String,String> get() =
        HashMap<String,String>().apply{
            this[ACCESS_TOKEN] = "Bearer ${prefs.accessToken}"
            this[REFRESH_TOKEN] = prefs.refreshToken
        }


    fun getCall(remoteApi : RemoteInterface) : Call<CResponse>
    {

        /**
         * API REDUCING RULE
         *
         * API NUM의 오름차순 정렬로 규칙을 작성해줍니다.
         *
         * 각각의 API에 대해, remoteApi 인터페이스의 메서드를 통해 호출한 Call<CResponse> 객체를 반환합니다.
         *
         * header 이외에 추가적으로 필요한 파라미터가 있다면, CallImpl의 파라미터에서 가져옵니다. 그럴경우 CallImpl을 호출하는 쪽에서 데이터를 지정해줘야합니다.
         *
         */
        return when(apiNum)
        {
            API_REFRESH_TOKEN -> remoteApi.refreshToken(header,requestBody as TokenRequest)


            API_LOGIN_EMAIL -> remoteApi.loginEmail(header,requestBody as LoginEmailBody)


            API_EMAIL_GET_NAVER -> remoteApi.loginGetNaverEmail("Bearer $paramStr0")


            API_GET_MARKET_PRODUCTS -> remoteApi.getMarketProducts(header,paramInt0!!, paramInt1!!, paramStr0!!, paramStr1!!)

            API_JOIN_ACCOUNT -> remoteApi.joinAccount(header,requestBody as JoinRequest)

            API_BUILD_MARKET_PRODUCT ->
                remoteApi.buildMarketProduct(header,requestBody as ProductBuildRequest, multipartList!!)

            else -> throw NoSuchMethodException()
        } as Call<CResponse>
    }


}