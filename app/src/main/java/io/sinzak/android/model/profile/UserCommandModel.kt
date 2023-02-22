package io.sinzak.android.model.profile

import io.sinzak.android.constants.API_DELETE_ALL_SEARCH_HISTORY
import io.sinzak.android.constants.API_DELETE_SEARCH_HISTORY
import io.sinzak.android.constants.API_GET_SEARCH_HISTORY
import io.sinzak.android.constants.API_REPORT_USER
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.profile.HistoryRequest
import io.sinzak.android.remote.dataclass.request.profile.ReportRequest
import io.sinzak.android.remote.retrofit.CallImpl
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserCommandModel @Inject constructor() : BaseModel(){

    private var historyId = ""

    val reportSuccessFlag = MutableStateFlow(false)

    /************************************************
     * Local Data Insert
     ***************************************/

    fun setHistoryId (h : String)
    {
        historyId = h
    }

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

    fun getSearchHistory()
    {
        CallImpl(
            API_GET_SEARCH_HISTORY,
            this,
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun deleteSearchHistory(id : String)
    {
        val request = HistoryRequest(id = id)
        CallImpl(
            API_DELETE_SEARCH_HISTORY,
            this,
            request
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    fun deleteAllSearchHistory()
    {
        CallImpl(
            API_DELETE_ALL_SEARCH_HISTORY,
            this,
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
                    globalUi.showToast("신고되었습니다")
                }
                else {
                    reportSuccessFlag.value = false
                    globalUi.showToast(body.message.toString())
                }
            }

            API_DELETE_SEARCH_HISTORY -> {
                if (body.success == true)
                {
                    globalUi.showToast("검색 기록 삭제")
                }
            }

            API_DELETE_ALL_SEARCH_HISTORY -> {
                if (body.success == true)
                {
                    globalUi.showToast("전체 검색 기록 삭제")
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