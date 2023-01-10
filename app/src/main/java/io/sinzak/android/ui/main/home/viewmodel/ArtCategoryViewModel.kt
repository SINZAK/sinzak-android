package io.sinzak.android.ui.main.home.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.MarketArtModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.home.adapter.CategoryAdapter
import javax.inject.Inject

@HiltViewModel
class ArtCategoryViewModel @Inject constructor(val marketArtModel: MarketArtModel) : HomeLinearViewModel() {


    override val adapter = CategoryAdapter(valueModel.categoryMarket){
        marketArtModel.setCategoryString(it)
        navigation.changePage(Page.MARKET)


    }

    override var title: String = valueModel.getString(R.string.str_home_title_category)

    override val hMargin: Float = 10f

}