package io.sinzak.android.model.market

import android.util.Log
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.sinzak.android.constants.API_DELETE_ALL_SEARCH_HISTORY
import io.sinzak.android.constants.API_GET_SEARCH_HISTORY
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.history.HistoryData
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
    private val _history = MutableStateFlow(listOf<List<String>>())

    override fun getHistoryList(): StateFlow<List<List<String>>> {
        LogDebug(javaClass.name, "리사이클러뷰 데이터 배정")
        return _history
    }

    override fun clearHistory() {
    }

    override fun deleteHistory(tag: List<String>) {

    }


    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api)
        {
            API_GET_SEARCH_HISTORY ->
            {
                onGetHistoryResponse(body as HistoryResponse)
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

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
            _history.value = response.data.toMutableList()
            LogDebug(javaClass.name, "리스펀스 데이터 모델로 받아옴")
        }
    }


    fun putHistory(tag : String)
    {
        if(_historyList.value.contains(tag))
            return
        val list = _historyList.value.toMutableList()
        list.add(tag)
        _historyList.value = list
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