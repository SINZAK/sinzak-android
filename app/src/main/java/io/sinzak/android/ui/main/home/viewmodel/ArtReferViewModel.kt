package io.sinzak.android.ui.main.home.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.constants.CODE_USER_NAME
import io.sinzak.android.enums.HomeMore
import io.sinzak.android.enums.Page
import io.sinzak.android.enums.Sort
import io.sinzak.android.model.market.HomeProductModel
import io.sinzak.android.model.market.MarketArtModel
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.ui.main.home.adapter.ArtLinearAdapter
import javax.inject.Inject
import io.sinzak.android.system.App.Companion.prefs

@HiltViewModel
class ArtReferViewModel @Inject constructor(
    val model : HomeProductModel,
    val productDetailModel: ProductDetailModel,
    val marketArtModel: MarketArtModel
) : HomeLinearViewModel() {
    override val adapter = ArtLinearAdapter(
        onNextClick = {
            if (!isUserLogin) {
                marketArtModel.setMarketSort(Sort.BY_REFER)
                navigation.changePage(Page.MARKET)
            }
            else {
                model.morePageType.value = HomeMore.REFER
                navigation.changePage(Page.HOME_MORE)
            }
        },
        onLikeClick = productDetailModel::postProductLike
    ){
        uiModel.openProductDetail()
        productDetailModel.loadProduct(it.id!!)
    }
    override val title: String
        get() =
            if (isUserLogin) String.format(valueModel.getString(
            R.string.str_art_refer_title
        ), prefs.getString(CODE_USER_NAME,""))
            else valueModel.getString(R.string.str_home_refer_null_title)


    override val hMargin: Float
        get() = 20f


    init{




        invokeStateFlow(model.recommendProducts){
            adapter.updateData(it)
        }



    }



}