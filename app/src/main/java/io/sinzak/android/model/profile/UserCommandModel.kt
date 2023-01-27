package io.sinzak.android.model.profile

import io.sinzak.android.constants.API_REPORT_USER
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.profile.ReportRequest
import io.sinzak.android.remote.retrofit.CallImpl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserCommandModel @Inject constructor() : BaseModel(){

    private var reportReason = ""


    /************************************************
     * Local Data Insert
     ***************************************/
    fun setReason(r : String)
    {
        reportReason = r
    }

    /**********************************************************************************************************************
     * REQUEST ( MARKET PRODUCT )
     ***********************************************************************************************************************/

    fun reportUser(userId : String)
    {
        val request = ReportRequest(
            reason = reportReason,
            userId = userId
        )
        CallImpl(
            API_REPORT_USER,
            this,
            request
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api)
        {
            API_REPORT_USER -> {
                if (body.success == true)
                {
                    globalUi.showToast("신고되었습니다")
                }
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {
    }
}