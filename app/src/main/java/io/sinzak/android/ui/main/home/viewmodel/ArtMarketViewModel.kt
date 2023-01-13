package io.sinzak.android.ui.main.home.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.model.market.HomeProductModel
import io.sinzak.android.ui.main.home.adapter.ArtLinearAdapter
import javax.inject.Inject

@HiltViewModel
class ArtMarketViewModel @Inject constructor(
    val model : HomeProductModel
) : HomeLinearViewModel() {
    override val adapter = ArtLinearAdapter()

    override val hMargin: Float
        get() = 20f

    override var title: String
     = valueModel.getString(R.string.str_art_onmarket_title)

    init{

        invokeStateFlow(model.tradingProducts){
            adapter.updateData(it)
        }
    }

}