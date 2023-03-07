package io.sinzak.android.model.profile

import io.sinzak.android.constants.API_CANCEL_REPORT
import io.sinzak.android.constants.API_GET_REPORT_LIST
import io.sinzak.android.constants.API_REPORT_USER
import io.sinzak.android.constants.CODE_USER_NAME
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.profile.UserProfile
import io.sinzak.android.remote.dataclass.request.profile.ReportRequest
import io.sinzak.android.remote.dataclass.response.profile.ReportListResponse
import io.sinzak.android.remote.retrofit.CallImpl
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton
import io.sinzak.android.system.App.Companion.prefs
import io.sinzak.android.system.LogDebug
import kotlinx.coroutines.flow.StateFlow

@Singleton
class UserCommandModel @Inject constructor() : BaseModel(){

    val reportSuccessFlag = MutableStateFlow(false)

    private val _reportList = MutableStateFlow(mutableListOf<UserProfile>())
    val reportList : StateFlow<MutableList<UserProfile>> get() = _reportList

    /**********************************************************************************************************************
     * REQUEST
     ***********************************************************************************************************************/

    fun reportUser(reason : String ,userId : String)
    {
        val request = ReportRequest(
            reason = reason.trim(),
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

    fun blockUser(userId: String, blockUserName : String)
    {
        val userName = prefs.getString(CODE_USER_NAME,"")

        val reason = "$userName 님이 $blockUserName 님을 차단합니다"

        val request = ReportRequest(
            userId = userId,
            reason = reason
        )
        CallImpl(
            API_REPORT_USER,
            this,
            request
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun getReportList()
    {
        _reportList.value = mutableListOf()
        CallImpl(
            API_GET_REPORT_LIST,
            this
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun cancelReport(userId: String){
        val request = ReportRequest(userId = userId, reason = "")
        CallImpl(
            API_CANCEL_REPORT,
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
                if (body.success == true) {
                    reportSuccessFlag.value = true
                }
                else {
                    reportSuccessFlag.value = false
                    globalUi.showToast(body.message.toString())
                }
            }

            API_GET_REPORT_LIST -> {
                body as ReportListResponse
                body.data?.let {
                    _reportList.value = it.toMutableList().asReversed()
                }
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

        when(api)
        {
            API_REPORT_USER -> {
                globalUi.showToast(msg.toString())
            }
        }
    }
}