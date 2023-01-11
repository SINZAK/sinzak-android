package io.sinzak.android.ui.main.home.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
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
) : HomeLinearViewModel() {
    override val adapter = ArtReferAdapter{
        navigation.changePage(Page.ART_DETAIL)
        pModel.loadProduct(it.id.toString())
    }

    override val hMargin: Float
        get() = 20f

    override var title: String
        = valueModel.getString(R.string.str_home_recent)

    init{
        invokeStateFlow(model.newProducts){
            adapter.updateData(it)
        }
    }

}