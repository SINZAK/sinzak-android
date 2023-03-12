package io.sinzak.android.ui.main.home.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.enums.Page
import io.sinzak.android.enums.Sort
import io.sinzak.android.model.market.HomeProductModel
import io.sinzak.android.model.market.MarketArtModel
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.ui.main.home.adapter.ArtLinearAdapter
import javax.inject.Inject

@HiltViewModel
class ArtHotViewModel @Inject constructor(
    val model : HomeProductModel,
    val marketArtModel: MarketArtModel,
    val productDetailModel: ProductDetailModel
) : HomeLinearViewModel() {

    override val adapter = ArtLinearAdapter(
        onNextClick = {
            marketArtModel.setMarketSort(Sort.BY_REFER)
            navigation.changePage(Page.MARKET)
        }
    ) {
        productDetailModel.loadProduct(it.id!!)
        useFlag(productDetailModel.productLoadSuccessFlag) {
            uiModel.openProductDetail()
        }
    }


    override val title: String
        get() = valueModel.getString(R.string.str_home_refer_null_title)

    override val hMargin: Float
        get() = 20f

    init {

        invokeStateFlow(model.hotProducts){
            adapter.updateData(it)
        }
    }
}