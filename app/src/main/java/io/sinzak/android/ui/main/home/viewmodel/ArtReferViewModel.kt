package io.sinzak.android.ui.main.home.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.HomeProductModel
import io.sinzak.android.model.market.MarketProductModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.home.adapter.ArtReferAdapter
import javax.inject.Inject

@HiltViewModel
class ArtReferViewModel @Inject constructor(
    val model : HomeProductModel,
    val marketProductModel: MarketProductModel
) : BaseViewModel() {
    val adapter = ArtReferAdapter{
        uiModel.openProductDetail()
        marketProductModel.loadProduct(it.id.toString())
    }


    init{
        invokeStateFlow(model.hotProducts){
            adapter.updateData(it)
        }
    }



}