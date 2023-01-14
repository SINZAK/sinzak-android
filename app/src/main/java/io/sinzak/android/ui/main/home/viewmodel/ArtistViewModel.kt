package io.sinzak.android.ui.main.home.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.enums.HomeMore
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.HomeProductModel
import io.sinzak.android.model.market.MarketProductModel
import io.sinzak.android.ui.main.home.adapter.ArtLinearAdapter
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    val model : HomeProductModel,
    val detailModel : MarketProductModel
) : HomeLinearViewModel() {
    override val adapter = ArtLinearAdapter(
        onNextClick = {
            model.morePageType.value = HomeMore.FOLLOWING
            navigation.changePage(Page.HOME_MORE)
        },
        onLikeClick = detailModel::postProductLike
    ){
        detailModel.loadProduct(it.id!!)
        uiModel.openProductDetail()
    }
    override val hMargin: Float
        get() = 20f

    override var title: String
            = valueModel.getString(R.string.str_home_artist_title)

}