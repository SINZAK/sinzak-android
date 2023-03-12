package io.sinzak.android.model.market

import io.sinzak.android.constants.*
import io.sinzak.android.model.BaseModel
import io.sinzak.android.model.chat.ChatStorage
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.market.ProductFormRequest
import io.sinzak.android.remote.dataclass.request.market.ProductSuggestRequest
import io.sinzak.android.remote.dataclass.response.market.MarketDetailResponse
import io.sinzak.android.remote.retrofit.CallImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ProductDetailModel @Inject constructor() : BaseModel() {

    @Inject lateinit var chatStorage: ChatStorage

    val art = MutableStateFlow<MarketDetailResponse.Detail?>(null)

    val productSuggestSuccessFlag = MutableStateFlow(false)
    val productDeleteSuccessFlag = MutableStateFlow(false)

    val productStatusUpdateFlag = MutableStateFlow(false)

    val itemType = MutableStateFlow(TYPE_MARKET_PRODUCT) // 0 : product, 1 : work

    /**
     * 가격 제안을 위한 아이디 세팅
     */
    val productId = MutableStateFlow(0)

    fun setIdForSuggest(id : Int)
    {
        productId.value = id
    }

    fun getProductStatusStr(complete : Boolean) : String {
        if (itemType.value == TYPE_MARKET_PRODUCT){
            return if (complete) "거래완료" else "거래중"
        }
        else {
            return if (complete) "모집완료" else "모집중"
       }
    }

    fun loadProduct(id : Int){
        art.value = null
        itemType.value = TYPE_MARKET_PRODUCT
        CallImpl(API_GET_PRODUCT_DETAIL, this,paramInt0 = id).apply{
            remote.sendRequestApi(this)
        }
    }

    fun loadWork(id : Int){
        itemType.value = TYPE_MARKET_WORK
        art.value = null
        remote.sendRequestApi(
            CallImpl(API_GET_MARKET_WORK_DETAIL, this, paramInt0 = id)
        )
    }

    fun postProductLike(id : Int, status : Boolean){
        remote.sendRequestApi(
            CallImpl(
                if(itemType.value == TYPE_MARKET_PRODUCT)
                API_POST_LIKE_PRODUCT
                else API_POST_LIKE_WORK
                , this,
                ProductFormRequest(
                    id,
                    status
                )
            )
        )
    }


    fun makeNewChatroom(){
        art.value?.let{
            chatStorage.makeChatroom(it, if(itemType.value == 0) "product" else "work")
        }

        //이후 Chatroom Fragment로 이동
    }



    fun postProductWish(id : Int, status : Boolean){
        remote.sendRequestApi(
            CallImpl(if(itemType.value == TYPE_MARKET_PRODUCT)
                API_POST_WISH_PRODUCT
            else API_POST_WISH_WORK, this,
                ProductFormRequest(
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
                if(itemType.value == TYPE_MARKET_PRODUCT)
                    API_POST_SUGGEST_PRODUCT
                else API_POST_SUGGEST_WORK
               , this, ProductSuggestRequest(
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
                if (itemType.value == TYPE_MARKET_PRODUCT) API_DELETE_MARKET_PRODUCT
                else API_DELETE_MARKET_WORK,
                this,
                paramInt0 = id
            )
        )
    }

    /**
     * 거래완료 상태로 업데이트 합니다
     */
    fun updateProductState(id : Int, isProduct : Boolean)
    {
        CallImpl(
            if (isProduct) API_POST_SELL_STATE
            else API_POST_WORK_STATE,
            this,
            paramInt0 = id
        ).apply {
            remote.sendRequestApi(this)
        }
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

            API_POST_SUGGEST_PRODUCT, API_POST_SUGGEST_WORK ->{
                if(body.success == true)
                    productSuggestSuccessFlag.value = true
                else
                    globalUi.showToast(body.message.toString())
            }



            API_DELETE_MARKET_PRODUCT,
            API_DELETE_MARKET_WORK->{
                if(body.success == true)
                    productDeleteSuccessFlag.value = true
                else
                    globalUi.showToast(body.message.toString())
            }

            API_GET_MARKET_WORK_DETAIL ->{
                if(body is MarketDetailResponse){
                    art.value = body.data
                }else
                    globalUi.showToast(body.message.toString())
            }

            API_POST_SELL_STATE,
            API_POST_WORK_STATE -> {
                productStatusUpdateFlag.value = body.success!!
                if (!body.success) globalUi.showToast(body.message.toString())
            }

        }
    }




}