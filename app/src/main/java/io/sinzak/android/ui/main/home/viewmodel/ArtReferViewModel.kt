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
class ArtReferViewModel @Inject constructor(
    val model : HomeProductModel,
    val marketProductModel: MarketProductModel
) : HomeLinearViewModel() {
    override val adapter = ArtReferAdapter{
        uiModel.openProductDetail()
        marketProductModel.loadProduct(it.id.toString())
    }

    override val hMargin: Float
        get() = 20f

    override var title: String = ""
        get() =
        String.format(valueModel.getString(
            R.string.str_art_refer_title
        ),
        signModel.getUserDisplayName())



    init{
        invokeStateFlow(model.hotProducts){
            adapter.updateData(it)
        }
    }



}