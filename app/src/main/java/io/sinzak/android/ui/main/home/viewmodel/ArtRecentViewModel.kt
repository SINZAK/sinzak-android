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
class ArtRecentViewModel @Inject constructor(
    val model : HomeProductModel,
    val pModel : ProductDetailModel,
    val marketArtModel: MarketArtModel
) : HomeLinearViewModel() {
    override val adapter = ArtLinearAdapter(
        onNextClick = {
            marketArtModel.setMarketSort(Sort.BY_RECENT)
            navigation.changePage(Page.MARKET)
        },
        onLikeClick = pModel::postProductLike

    ){
        pModel.loadProduct(it.id!!)
        useFlag(pModel.productLoadSuccessFlag){
            navigation.changePage(Page.ART_DETAIL)
        }

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