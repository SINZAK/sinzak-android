package io.sinzak.android.ui.main.home.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.constants.CODE_USER_NAME
import io.sinzak.android.enums.HomeMore
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.HomeProductModel
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.ui.main.home.adapter.ArtLinearAdapter
import javax.inject.Inject
import io.sinzak.android.system.App.Companion.prefs

@HiltViewModel
class ArtReferViewModel @Inject constructor(
    val model : HomeProductModel,
    val productDetailModel: ProductDetailModel
) : HomeLinearViewModel() {
    override val adapter = ArtLinearAdapter(
        onNextClick = {
            model.morePageType.value = HomeMore.REFER
            navigation.changePage(Page.HOME_MORE)
        },
        onLikeClick = productDetailModel::postProductLike
    ){
        productDetailModel.loadProduct(it.id!!)
        useFlag(productDetailModel.productLoadSuccessFlag){
            uiModel.openProductDetail()
        }

    }
    override val title: String
        get() = String.format(valueModel.getString(
            R.string.str_art_refer_title
        ), prefs.getString(CODE_USER_NAME,""))


    override val hMargin: Float
        get() = 20f


    init{

        invokeStateFlow(model.recommendProducts){
            adapter.updateData(it)
        }


    }



}