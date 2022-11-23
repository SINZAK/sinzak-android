package io.sinzak.android.ui.main.market.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Sort
import io.sinzak.android.model.market.MarketArtModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SortViewModel @Inject constructor(val md : MarketArtModel) : BaseViewModel() {

    val sortOrder: StateFlow<Sort> = md.sortOrder

    fun setMarketSort(sort: Sort)
    {
        md.setMarketSort(sort)
    }

}