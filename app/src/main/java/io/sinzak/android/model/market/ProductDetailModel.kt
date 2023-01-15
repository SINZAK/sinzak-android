package io.sinzak.android.model.market

import io.sinzak.android.constants.*
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.market.ProductLikeRequest
import io.sinzak.android.remote.dataclass.request.market.ProductSuggestRequest
import io.sinzak.android.remote.dataclass.response.market.MarketDetailResponse
import io.sinzak.android.remote.retrofit.CallImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ProductDetailModel @Inject constructor() : BaseModel() {

    val art = MutableStateFlow<MarketDetailResponse.Detail?>(null)

    val productSuggestSuccessFlag = MutableStateFlow(false)
    val productDeleteSuccessFlag = MutableStateFlow(false)


    val itemType = MutableStateFlow(0) // 0 : product, 1 : work



    fun loadProduct(id : Int){
        art.value = null
        itemType.value = 0
        CallImpl(API_GET_PRODUCT_DETAIL, this,paramInt0 = id).apply{
            remote.sendRequestApi(this)
        }
    }

    fun loadWork(id : Int){
        itemType.value = 1
    }






    fun postProductLike(id : Int, status : Boolean){
        remote.sendRequestApi(
            CallImpl(API_POST_LIKE_PRODUCT, this,
                ProductLikeRequest(
                    id,
                    status
                )
            )
        )
    }






    fun postProductWish(id : Int, status : Boolean){
        remote.sendRequestApi(
            CallImpl(API_POST_WISH_PRODUCT, this,
                ProductLikeRequest(
                    id,
                    status
                )
            )
        )
    }

    fun postProductSuggest(id : Int, price : Int){
        productSuggestSuccessFlag.value = false
        remote.sendRequestApi(
            CallImpl(
                API_POST_SUGGEST_PRODUCT, this, ProductSuggestRequest(
                    id = id,
                    price = price
                )
            )
        )
    }

    fun deleteProduct(id : Int){
        productDeleteSuccessFlag.value = false
        remote.sendRequestApi(
            CallImpl(
                API_DELETE_MARKET_PRODUCT, this,
                paramInt0 = id
            )
        )
    }






    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }

    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api){
            API_GET_PRODUCT_DETAIL ->{
                if(body is MarketDetailResponse){
                    art.value = body.data
                }
            }

            API_POST_SUGGEST_PRODUCT ->{
                if(body.success == true)
                    productSuggestSuccessFlag.value = true
                else
                    globalUi.showToast(body.message.toString())
            }

            API_DELETE_MARKET_PRODUCT ->{
                if(body.success == true)
                    productDeleteSuccessFlag.value = true
                else
                    globalUi.showToast(body.message.toString())
            }
        }
    }
}