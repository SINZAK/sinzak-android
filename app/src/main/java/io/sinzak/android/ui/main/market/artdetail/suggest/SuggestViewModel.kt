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


    /*************************************
     * Click Event
     ****************************************/


    fun onClickSuggest(){
        model.postProductSuggest(model.productId.value, PriceUtil.makePriceInt(price.value))
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

        collectSuccessFlag()
    }


    private fun collectSuccessFlag(){
        useFlag(model.productSuggestSuccessFlag){
            navigation.revealHistory()
            uiModel.showToast(valueModel.getString(R.string.str_send_suggest))
        }
    }

    fun addCommaToNumber(): String {
        return PriceUtil.getFormattedPrice(model.art.value!!.topPrice)
    }

    fun onBackPressed() {
        navigation.revealHistory()
    }
}