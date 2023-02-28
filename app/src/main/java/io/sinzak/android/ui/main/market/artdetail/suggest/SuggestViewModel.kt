package io.sinzak.android.ui.main.market.artdetail.suggest

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class SuggestViewModel @Inject constructor(
    val model : ProductDetailModel
) : BaseViewModel() {

    private var price : Int = 0
    val priceInserted = MutableStateFlow(false)
    private var productId : Int = -1


    /*************************************
     * Click Event
     ****************************************/


    fun onClickSuggest(){
        model.postProductSuggest(productId, price)

    }


    /*************************************
     * Type Event
     ****************************************/

    fun onTypePrice(cs : CharSequence){
        priceInserted.value = cs.toString().isNotEmpty()
        price = cs.toString().toInt()
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
            uiModel.showToast("가격제안을 전송했습니다.")
        }
    }

    fun addCommaToNumber(number: Int): String {
        val formatter = DecimalFormat("#,###")
        return formatter.format(number)
    }
}