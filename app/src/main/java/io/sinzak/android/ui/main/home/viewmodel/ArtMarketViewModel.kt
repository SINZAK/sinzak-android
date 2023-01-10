package io.sinzak.android.ui.main.home.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.market.HomeProductModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.home.adapter.ArtReferAdapter
import javax.inject.Inject

@HiltViewModel
class ArtMarketViewModel @Inject constructor(
    val model : HomeProductModel
) : BaseViewModel() {
    val adapter = ArtReferAdapter()

    init{
        invokeStateFlow(model.tradingProducts){
            adapter.updateData(it)
        }
    }

}