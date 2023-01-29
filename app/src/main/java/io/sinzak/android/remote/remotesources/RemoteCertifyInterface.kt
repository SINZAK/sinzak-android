package io.sinzak.android.remote.remotesources

import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.certify.MailRequest
import io.sinzak.android.remote.dataclass.request.certify.UnivCertifyRequest
import io.sinzak.android.remote.dataclass.response.certify.UnivCertifyResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RemoteCertifyInterface {

    @Multipart
    @POST("api/certify/{id}/univ")
    fun uploadUnivImg(@HeaderMap headerMap : HashMap<String,String>, @Path("id") id : String, @Part part: MultipartBody.Part) : Call<CResponse>

    @POST("api/certify/mail/receive")
    fun checkMailCode(@HeaderMap headerMap: HashMap<String, String>, @Body body : MailRequest) : Call<CResponse>

    @POST("api/certify/mail/send")
    fun sendMailCode(@HeaderMap headerMap: HashMap<String, String>, @Body body: MailRequest) : Call<CResponse>

    @POST("api/certify/univ")
    fun certifyUniversity(@HeaderMap headerMap: HashMap<String, String>, @Body body: UnivCertifyRequest) : Call<UnivCertifyResponse>

}