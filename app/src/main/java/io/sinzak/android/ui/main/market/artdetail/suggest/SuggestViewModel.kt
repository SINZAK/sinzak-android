package io.sinzak.android.ui.main.market.artdetail.suggest

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.utils.PriceUtil
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class SuggestViewModel @Inject constructor(
    val model : ProductDetailModel
) : BaseViewModel() {

    val price = MutableStateFlow("")
    private var productId : Int = -1


    /*************************************
     * Click Event
     ****************************************/


    fun onClickSuggest(){
        model.postProductSuggest(productId, PriceUtil.makePriceInt(price.value))
    }


    /*************************************
     * Type Event
     ****************************************/

    fun onTypePrice(cs : CharSequence){
        price.value = cs.toString()
    }


    /*************************************
     * DATA FLOW
     ****************************************/

    init{
        collectArt()
        collectSuccessFlag()
    }

    private fun collectArt(){
        invokeStateFlow(model.art){
            it?.let{p->
                productId = p.productId
            }

        }
    }

    private fun collectSuccessFlag(){
        useFlag(model.productSuggestSuccessFlag){
            navigation.revealHistory()
            uiModel.showToast(valueModel.getString(R.string.str_send_suggest))
        }
    }

    fun addCommaToNumber(number: Int): String {
        return PriceUtil.getFormattedPrice(number)
    }

    fun onBackPressed() {
        navigation.revealHistory()
    }
}