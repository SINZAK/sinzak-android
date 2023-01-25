package io.sinzak.android.model.certify

import io.sinzak.android.constants.API_CHECK_MAIL_CODE
import io.sinzak.android.constants.API_SEND_MAIL_CODE
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.local.SchoolData
import io.sinzak.android.remote.dataclass.request.certify.MailRequest
import io.sinzak.android.remote.retrofit.CallImpl
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CertifyModel @Inject constructor() : BaseModel() {

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

    fun sendMailCode()
    {
        val request = MailRequest(
            address = address,
            code = "",
            univ = univ.toString()
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
            univ = univ.toString()
        )

        CallImpl(
            API_CHECK_MAIL_CODE,
            this,
            request
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
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {
    }
}