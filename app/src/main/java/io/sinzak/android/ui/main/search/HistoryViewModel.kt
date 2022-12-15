package io.sinzak.android.ui.main.search

import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.market.MarketHistoryModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Qualifier


class HistoryViewModel @Inject constructor(val history : History) : BaseViewModel() {



    interface History{
        fun getHistoryList() : StateFlow<List<String>>

        fun clearHistory()

        fun deleteHistory(tag : String)

        val adapter : HistoryAdapter
    }


    @dagger.Module
    @InstallIn(FragmentComponent::class)
    internal object Module{

        @Provides
        @MarketHistory
        fun provideMarketHistoryViewModel(historyModel: MarketHistoryModel) : HistoryViewModel
        {
            return HistoryViewModel(historyModel)
        }


    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MarketHistory
}