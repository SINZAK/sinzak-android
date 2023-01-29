package io.sinzak.android.model.certify

import android.content.Context
import android.graphics.Bitmap
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import io.sinzak.android.constants.API_CERTIFY_UNIVERSITY
import io.sinzak.android.constants.API_CERTIFY_UPLOAD_IMG
import io.sinzak.android.constants.API_CHECK_MAIL_CODE
import io.sinzak.android.constants.API_SEND_MAIL_CODE
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.local.SchoolData
import io.sinzak.android.remote.dataclass.request.certify.MailRequest
import io.sinzak.android.remote.dataclass.request.certify.UnivCertifyRequest
import io.sinzak.android.remote.dataclass.response.certify.UnivCertifyResponse
import io.sinzak.android.remote.retrofit.CallImpl
import io.sinzak.android.utils.FileUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CertifyModel @Inject constructor(@ApplicationContext val context : Context) : BaseModel() {

    val flagCodeSuccess = MutableStateFlow(false)
    val flagCodeFailed = MutableStateFlow(false)

    /**
     * 이메일
     */
    private var address : String = ""
    fun setAddress(a : String)
    {
        address = a
    }

    /**
     * 코드
     */
    private var code : String = ""
    fun setCode(c : String)
    {
        code = c
    }

    /**
     * 학교
     */
    private var univ : SchoolData? = null
    fun setUniv(u : SchoolData)
    {
        univ = u
    }
    fun getUnivAddress() : String{
        return univ!!.schoolDomain
    }

    /************************************************
     * Image
     ***************************************/

    /**
     * 학생증 이미지
     */
    var imgUri = ""
    private var imgBitmap : Bitmap? = null

    /**
     * 불러온 local uri 을 bitmap 으로 불러옵니다.
     */
    private fun loadBitmaps()
    {
        CoroutineScope(Dispatchers.IO).launch {
            if (!imgUri.contains("http")) imgBitmap = FileUtil.getBitmapFile(context, imgUri.toUri())
        }
    }


    /**********************************************************************************************************************
     * REQUEST
     ***********************************************************************************************************************/
    fun sendMailCode()
    {
        val request = MailRequest(
            address = address,
            code = "",
            univ = univ!!.schoolName
        )

        CallImpl(
            API_SEND_MAIL_CODE,
            this,
            request
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun checkMailCode()
    {
        flagCodeFailed.value = false
        flagCodeSuccess.value = false


        val request = MailRequest(
            address = address,
            code = code,
            univ = univ!!.schoolName
        )

        CallImpl(
            API_CHECK_MAIL_CODE,
            this,
            request
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun certifySchoolId()
    {
        val request = UnivCertifyRequest(
            univ = univ!!.schoolName,
            univ_email = univ!!.schoolDomain
        )
        CallImpl(
            API_CERTIFY_UNIVERSITY,
            this,
            request
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun uploadImg(id : String)
    {
        loadBitmaps()
        val requestBody = FileUtil.getMultipart(context,"multipartFile",imgBitmap!!)

        CallImpl(
            API_CERTIFY_UPLOAD_IMG,
            this,
            paramStr0 = id,
            multipart = requestBody
        ).apply {
            remote.sendRequestApi(this)
        }
    }


    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api)
        {
            API_SEND_MAIL_CODE -> {
                if (body.success == true){
                    globalUi.showToast("메일을 전송했습니다.")
                }
            }

            API_CHECK_MAIL_CODE -> {
                if (body.success == true){
                    flagCodeSuccess.value = true
                }else
                    flagCodeFailed.value = true
            }

            API_CERTIFY_UNIVERSITY -> {
                body as UnivCertifyResponse
                uploadImg(body.id)
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }
}