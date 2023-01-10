package io.sinzak.android.ui.main.home.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.HomeProductModel
import io.sinzak.android.model.market.MarketProductModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.home.adapter.ArtReferAdapter
import javax.inject.Inject

@HiltViewModel
class ArtRecentViewModel @Inject constructor(
    val model : HomeProductModel,
    val pModel : MarketProductModel
) : BaseViewModel() {
    val adapter = ArtReferAdapter{
        navigation.changePage(Page.ART_DETAIL)
        pModel.loadProduct(it.id.toString())
    }

    init{
        invokeStateFlow(model.newProducts){
            adapter.updateData(it)
        }
    }

}