package io.sinzak.android.remote.retrofit

import com.google.gson.Gson
import com.google.gson.JsonObject
import io.sinzak.android.constants.*
import io.sinzak.android.remote.dataclass.CRequest
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.certify.MailRequest
import io.sinzak.android.remote.dataclass.request.certify.UnivCertifyRequest
import io.sinzak.android.remote.dataclass.request.login.JoinRequest
import io.sinzak.android.remote.dataclass.request.login.LoginEmailBody
import io.sinzak.android.remote.dataclass.request.login.TokenRequest
import io.sinzak.android.remote.dataclass.request.market.ProductBuildRequest
import io.sinzak.android.remote.dataclass.request.market.ProductLikeRequest
import io.sinzak.android.remote.dataclass.request.market.ProductSuggestRequest
import io.sinzak.android.remote.dataclass.request.profile.FollowRequest
import io.sinzak.android.remote.dataclass.request.profile.UpdateUserRequest
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
    val paramStr2 : String? = null,
    val paramBool0 : Boolean? = null,
    val multipartList : List<MultipartBody.Part>? = null,
    val multipart : MultipartBody.Part? = null,
) {

    private val header : HashMap<String,String> get() =
        HashMap<String,String>().apply{
            this[CONTENT_TYPE] = "application/json"
            this[AUTHORIZATION] = prefs.accessToken
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

            API_GET_USER_PROFILE -> remoteApi.getUserProfile(header,paramStr0!!)

            API_FOLLOW_USER -> remoteApi.followUser(header, requestBody as FollowRequest)

            API_UNFOLLOW_USER -> remoteApi.unfollowUser(header, requestBody as FollowRequest)

            API_GET_FOLLOWING_LIST -> remoteApi.getFollowingList(header,paramStr0!!)

            API_GET_FOLLOWER_LIST -> remoteApi.getFollowerList(header, paramStr0!!)







            API_GET_MY_PROFILE -> remoteApi.getMyProfile(header)

            API_EDIT_MY_PROFILE -> remoteApi.editMyProfile(header,requestBody as UpdateUserRequest)

            API_CERTIFY_UPLOAD_IMG -> remoteApi.uploadUnivImg(header.apply {
                this.remove(CONTENT_TYPE) }, paramStr0!!, multipart!!)

            API_CERTIFY_UNIVERITY -> remoteApi.certifyUniversity(header, requestBody as UnivCertifyRequest)

            API_SEND_MAIL_CODE -> remoteApi.sendMailCode(header, requestBody as MailRequest)

            API_CHECK_MAIL_CODE -> remoteApi.checkMailCode(header, requestBody as MailRequest)

            API_JOIN_ACCOUNT -> remoteApi.joinAccount(header,requestBody as JoinRequest)







            API_GET_MARKET_PRODUCTS -> remoteApi.getMarketProducts(header,paramInt0!!, paramInt1!!, paramStr0!!, paramStr1!!, paramStr2!!, paramBool0!!)

            API_BUILD_MARKET_PRODUCT -> remoteApi.buildMarketProduct(header, requestBody as ProductBuildRequest)

            API_UPDATE_MARKET_PRODUCT -> remoteApi.editMarketProduct(header, paramInt0!!, requestBody as ProductBuildRequest)

            API_PRODUCT_UPLOAD_IMG -> remoteApi.uploadProductImage(header.apply{
                this.remove(CONTENT_TYPE)
            }, paramInt0!!, multipartList!!)

            API_PRODUCT_DELETE_IMG -> remoteApi.deleteProductImage(header,paramInt0!!, JsonObject().apply{
                addProperty("url",paramStr0!!)
            })

            API_DELETE_MARKET_PRODUCT -> remoteApi.deleteProduct(header,paramInt0!!)





            API_GET_MARKET_WORKS -> remoteApi.getMarketWorks(header,paramInt0!!, paramInt1!!, paramStr0!!, paramStr1!!, paramStr2!!, paramBool0!!)



            API_BUILD_MARKET_WORK -> remoteApi.buildMarketWork(header, requestBody as ProductBuildRequest)

            API_UPDATE_MARKET_WORK -> remoteApi.editMarketWork(header, paramInt0!!, requestBody as ProductBuildRequest)

            API_WORK_UPLOAD_IMG -> remoteApi.uploadWorkImage(header.apply{
                this.remove(CONTENT_TYPE)
            }, paramInt0!!, multipartList!!)

            API_WORK_DELETE_IMG -> remoteApi.deleteWorkImage(header,paramInt0!!, JsonObject().apply{
                addProperty("url",paramStr0!!)
            })

            API_DELETE_MARKET_WORK -> remoteApi.deleteWork(header,paramInt0!!)







            API_GET_PRODUCT_DETAIL -> remoteApi.getMarketProductDetail(header,paramInt0!!)

            API_GET_HOME_PRODUCTS -> remoteApi.getHomeProducts(header)

            API_GET_HOME_REFER -> remoteApi.getHomeRefer(header)

            API_GET_HOME_FOLLOWING -> remoteApi.getHomeFollowing(header)

            API_GET_HOME_BANNER -> remoteApi.getBanner(header)

            API_POST_LIKE_PRODUCT -> remoteApi.postProductLikes(header, requestBody as ProductLikeRequest)

            API_POST_WISH_PRODUCT -> remoteApi.postProductWish(header, requestBody as ProductLikeRequest)

            API_POST_SUGGEST_PRODUCT -> remoteApi.postProductSuggest(header, requestBody as ProductSuggestRequest)

            API_GET_MARKET_WORK_DETAIL -> remoteApi.getMarketWorkDetail(header, paramInt0!!)

            API_POST_LIKE_WORK -> remoteApi.postWorkLikes(header, requestBody as ProductLikeRequest)

            API_POST_SUGGEST_WORK -> remoteApi.postWorkSuggest(header, requestBody as ProductSuggestRequest)

            API_POST_WISH_WORK -> remoteApi.postWorkWish(header, requestBody as ProductLikeRequest)



            else -> throw NoSuchMethodException()
        } as Call<CResponse>
    }


}