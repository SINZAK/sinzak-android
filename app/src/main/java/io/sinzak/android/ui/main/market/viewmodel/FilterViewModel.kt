package io.sinzak.android.ui.main.market.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.market.MarketArtModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.market.adapter.FilterAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class FilterViewModel @Inject constructor(
    val marketArtModel: MarketArtModel
) : BaseViewModel() {

    val adapter = FilterAdapter(valueModel.categoryMarket.toMutableList()) {
        marketArtModel.setCategoryString(valueModel.makeRequestStr(it))
    }


    fun loadCategory(){
        marketArtModel.getCategoryString().let{
            filter->
            if(filter.isEmpty())
                return
            adapter.setCurrentFilter(filter)
        }
    }




}