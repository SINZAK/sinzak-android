package io.sinzak.android.model.certify

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
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
import io.sinzak.android.system.LogDebug
import io.sinzak.android.utils.FileUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CertifyModel @Inject constructor(@ApplicationContext val context : Context) : BaseModel() {

    val flagSendSuccess = MutableStateFlow(false)
    val flagCodeSuccess = MutableStateFlow(false)
    val flagCodeFailed = MutableStateFlow(false)
    val flagUploadSuccess = MutableStateFlow(false)

    private var univ : SchoolData? = null
    private var address : String = ""
    private var code : String = ""
    private var multiPart : MultipartBody.Part? = null

    val convertUriFlag = MutableStateFlow(false)

    val codeCheckCnt = MutableStateFlow(0)

    /************************************************
     * 입력을 받습니다
     ***************************************/

    /**
     * 학교를 저장합니다
     */
    fun setUniv(u : SchoolData)
    {
        univ = u
    }
    fun getUnivAddress() : String{
        return univ!!.schoolDomain
    }

    /**
     * 이메일을 저장합니다
     */
    fun setAddress(a : String)
    {
        address = a.trim()
    }

    /**
     * 인증 코드를 저장합니다
     */
    fun setCode(c : String)
    {
        code = c
    }

    /**
     * 이미지를 MultiPart로 변환
     */
    fun convertUriToMultiPart(uri: Uri)
    {
        CoroutineScope(Dispatchers.IO).launch {
            if (!uri.toString().contains("http"))
            {
                val imgBitmap : Bitmap = FileUtil.getBitmapFile(context, uri)

                multiPart = FileUtil.getMultipart(context,"multipartFile",imgBitmap)

                convertUriFlag.value = true
            }

            else {
                convertUriFlag.value = false
            }
        }
    }


    /************************************************
     * 저장값을 초기화합니다
     ***************************************/

    /**
     * 학교를 초기화합니다
     */
/*    fun clearUniv()
    {
        univ = null
    }*/


    /**********************************************************************************************************************
     * API 요청
     ***********************************************************************************************************************/

    /**
     * 인증 메일을 보냅니다
     */
    fun sendMailCode()
    {
        val request = MailRequest(
            univEmail = address,
            univName = univ!!.schoolName,
            code = ""
        )

        CallImpl(
            API_SEND_MAIL_CODE,
            this,
            request
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    /**
     * 인증 코드를 확인합니다
     */
    fun checkMailCode()
    {
        flagCodeSuccess.value = false


        val request = MailRequest(
            univEmail = address,
            univName = univ!!.schoolName,
            code = code
        )

        CallImpl(
            API_CHECK_MAIL_CODE,
            this,
            request
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    /**
     * 학생증 사진을 인증합니다
     */
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

    /**
     * 학생증 이미지를 업로드합니다
     */
    private fun uploadImg(id : String)
    {
        if (multiPart == null) {
            LogDebug(javaClass.name, "변환 실패")
            globalUi.showToast("다른 이미지를 사용해주세요")
            return
        }

        val requestBody = multiPart

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
                flagSendSuccess.value = body.success == true
                if (body.success == false) globalUi.showToast(body.message.toString())
            }

            API_CHECK_MAIL_CODE -> {
                flagCodeSuccess.value = body.success == true

                if (body.success == false) {
                    codeCheckCnt.value += 1

                    if(codeCheckCnt.value >= 3)
                    {
                        globalUi.showToast("학생증 인증을 이용해주세요")
                        return
                    }
                    else globalUi.showToast("인증번호 ${codeCheckCnt.value}회 오류입니다")
                }
            }

            API_CERTIFY_UNIVERSITY -> {
                body as UnivCertifyResponse
                uploadImg(body.id)
                LogDebug(javaClass.name, body.id)
            }

            API_CERTIFY_UPLOAD_IMG -> {
                flagUploadSuccess.value = body.success == true
                if (body.success == false) globalUi.showToast(body.message.toString())
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

        when(api)
        {

            API_SEND_MAIL_CODE -> {
                flagSendSuccess.value = false
                globalUi.showToast(msg.toString())
            }
            API_CHECK_MAIL_CODE -> {
                flagCodeSuccess.value = false
                globalUi.showToast(msg.toString())
            }
            API_CERTIFY_UPLOAD_IMG -> {
                flagUploadSuccess.value = false
                globalUi.showToast(msg.toString())
            }
        }
    }
}