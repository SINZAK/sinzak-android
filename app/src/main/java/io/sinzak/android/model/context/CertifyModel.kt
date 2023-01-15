package io.sinzak.android.model.context

import io.sinzak.android.constants.API_CHECK_MAIL_CODE
import io.sinzak.android.constants.API_SEND_MAIL_CODE
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.certify.MailRequest
import io.sinzak.android.remote.retrofit.CallImpl
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CertifyModel @Inject constructor() : BaseModel() {

    private var address = ""
    private var code = ""
    private var univ = ""

    val flagCodeSuccess = MutableStateFlow(false)
    val flagCodeFailed = MutableStateFlow(false)

    fun setAddress(a : String)
    {
        address = a
    }

    fun setCode(c : String)
    {
        code = c
    }

    fun setUnvi(u : String)
    {
        univ = u
    }

    fun sendMailCode()
    {
        val request = MailRequest(
            address = address,
            code = "",
            univ = univ
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
            univ = univ
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