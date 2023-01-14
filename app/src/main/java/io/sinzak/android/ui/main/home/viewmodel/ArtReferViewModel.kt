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
class ArtReferViewModel @Inject constructor(
    val model : HomeProductModel,
    val marketProductModel: MarketProductModel
) : HomeLinearViewModel() {
    override val adapter = ArtLinearAdapter(
        onNextClick = {
            model.morePageType.value = HomeMore.REFER
            navigation.changePage(Page.HOME_MORE)
        },
        onLikeClick = marketProductModel::postProductLike
    ){
        uiModel.openProductDetail()
        marketProductModel.loadProduct(it.id!!)
    }

    override val hMargin: Float
        get() = 20f

    override var title: String = ""
        get() =
            signModel.getUserDisplayName().let{
                name->
                if(name.isEmpty())
                    valueModel.getString(R.string.str_home_refer_null_title)
                else
                    String.format(valueModel.getString(
                        R.string.str_art_refer_title
                    ),
                        name)

            }
       


    init{




        invokeStateFlow(model.recommendProducts){
            adapter.updateData(it)
        }



    }



}