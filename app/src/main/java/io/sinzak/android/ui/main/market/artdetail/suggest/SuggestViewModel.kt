package io.sinzak.android.ui.main.market.artdetail.suggest

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.market.MarketProductModel
import io.sinzak.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SuggestViewModel @Inject constructor(
    val model : MarketProductModel
) : BaseViewModel() {

    private var price : Int = 0

    private var productId : Int = -1



    /*************************************
     * Click Event
     ****************************************/


    fun onClickSuggest(){


    }


    /*************************************
     * Type Event
     ****************************************/

    fun onTypePrice(cs : CharSequence){
        price = cs.toString().toInt()
    }


    /*************************************
     * DATA FLOW
     ****************************************/

    init{
        collectArt()
    }

    private fun collectArt(){
        invokeStateFlow(model.art){
            it?.let{p->
                productId = p.productId
            }

        }
    }
}