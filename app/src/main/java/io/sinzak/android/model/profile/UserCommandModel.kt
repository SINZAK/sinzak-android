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
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserCommandModel @Inject constructor() : BaseModel(){

    private var reportReason = ""
    private var historyId = ""


    /************************************************
     * Local Data Insert
     ***************************************/
    fun setReason(r : String)
    {
        reportReason = r
    }
    fun setHistoryId (h : String)
    {
        historyId = h
    }

    /**********************************************************************************************************************
     * REQUEST
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
                if (body.success == true)
                {
                    globalUi.showToast("신고되었습니다")
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
    }
}