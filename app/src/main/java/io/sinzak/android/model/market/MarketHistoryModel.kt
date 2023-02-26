package io.sinzak.android.model.market

import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.sinzak.android.constants.API_DELETE_ALL_SEARCH_HISTORY
import io.sinzak.android.constants.API_DELETE_SEARCH_HISTORY
import io.sinzak.android.constants.API_GET_SEARCH_HISTORY
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.history.HistoryData
import io.sinzak.android.remote.dataclass.request.profile.HistoryRequest
import io.sinzak.android.remote.dataclass.response.history.HistoryResponse
import io.sinzak.android.remote.retrofit.CallImpl
import io.sinzak.android.system.LogDebug
import io.sinzak.android.ui.main.search.HistoryAdapter
import io.sinzak.android.ui.main.search.HistoryViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject



class MarketHistoryModel @Inject constructor(
    override val adapter : HistoryAdapter
    ) : BaseModel(), HistoryViewModel.History {

    private val _historyList = MutableStateFlow(listOf("신작", "아트"))

    /**
     * 검색 기록을 담는 공간
     */
    private val _history = MutableStateFlow(mutableListOf<HistoryData>())


    override fun getHistoryList(): StateFlow<List<HistoryData>> {
        return _history
    }

    override fun clearHistory() {
        requestDeleteAllHistory()
    }

    override fun deleteHistory(id: String) {
        requestDeleteHistory(id)
    }


    /**
     * 1. 서버에 검색 히스토리를 요청합니다
     */
    fun getRemoteHistoryList()
    {
        CallImpl(
            API_GET_SEARCH_HISTORY,
            this
        ).apply {
            remote.sendRequestApi(this)
        }
        LogDebug(javaClass.name, "api 콜")
    }

    /**
     * 2.  요청 성공 시 모델에 데이터를 받아옵니다
     */
    private fun onGetHistoryResponse(response: HistoryResponse)
    {
        response.data?.let {
            val list = mutableListOf<HistoryData>()
            for (i in it){
                val history = HistoryData(
                    id = i[0].trim(),
                    word = i[1].trim()
                )
                list.add(history)
            }
            _history.value = list
        }
    }

    /**
     *  검색 기록 삭제를 요청합니다
     */
    private fun requestDeleteHistory(id : String)
    {
        val request = HistoryRequest(id)
        CallImpl(
            API_DELETE_SEARCH_HISTORY,
            this,
            request
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    /**
     *  모든 검색 기록 삭제를 요청합니다
     */
    private fun requestDeleteAllHistory()
    {
        CallImpl(
            API_DELETE_ALL_SEARCH_HISTORY,
            this
        ).apply {
            remote.sendRequestApi(this)
        }
    }


    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api)
        {
            API_GET_SEARCH_HISTORY ->
            {
                onGetHistoryResponse(body as HistoryResponse)
            }

            API_DELETE_SEARCH_HISTORY -> {
                if (body.success == true)
                    globalUi.showToast("히스토리 삭제")
            }

            API_DELETE_ALL_SEARCH_HISTORY -> {
                if (body.success == true)
                    adapter.notifyItemRangeRemoved(0,adapter.itemCount)
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }


    fun putHistory(word : String)
    {
        if(containWord(word))
            return
        val list = _history.value.toMutableList()
        list.add(HistoryData("",word))
        _history.value = list
        adapter.notifyItemInserted(adapter.itemCount)
    }

    private fun containWord(word : String) : Boolean
    {
        for (i in _history.value)
        {
            if (i.word == word) return true
        }

        return false
    }



    @dagger.Module
    @InstallIn(ActivityComponent::class)
    internal object Module{

        @Provides
        fun provideAdapter() : HistoryAdapter{
            return HistoryAdapter()
        }

        @Provides
        fun provideMarketHistory(adapter : HistoryAdapter) : MarketHistoryModel{
            return MarketHistoryModel(adapter)
        }
    }

}