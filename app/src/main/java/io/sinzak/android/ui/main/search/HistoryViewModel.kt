package io.sinzak.android.ui.main.search

import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class HistoryViewModel @Inject constructor() : BaseViewModel() {

    lateinit var history : History


    interface History{
        fun getHistoryList() : StateFlow<List<String>>

        fun clearHistory()
    }
}