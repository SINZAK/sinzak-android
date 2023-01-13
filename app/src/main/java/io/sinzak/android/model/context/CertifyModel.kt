package io.sinzak.android.model.context

import io.sinzak.android.constants.API_CHECK_MAIL_CODE
import io.sinzak.android.constants.API_SEND_MAIL_CODE
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.certify.MailRequest
import io.sinzak.android.remote.retrofit.CallImpl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CertifyModel @Inject constructor() : BaseModel() {

    private var address = ""
    private var code = ""
    private var univ = ""

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

                }
            }

            API_CHECK_MAIL_CODE -> {
                if (body.success == true){

                }
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {
    }
}