package io.sinzak.android.model.market

import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.ui.main.search.HistoryAdapter
import io.sinzak.android.ui.main.search.HistoryViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject



class MarketHistoryModel @Inject constructor(override val adapter : HistoryAdapter) : BaseModel(), HistoryViewModel.History {

    private val _historyList = MutableStateFlow(listOf<String>("신작","게임아트"))
    override fun getHistoryList(): StateFlow<List<String>> {
        return _historyList
    }

    override fun clearHistory() {

    }

    override fun deleteHistory(tag: String) {

    }



    override fun onConnectionSuccess(api: Int, body: CResponse) {

    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

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