package io.sinzak.android.model.market

import io.sinzak.android.constants.API_GET_PRODUCT_DETAIL
import io.sinzak.android.constants.API_PRODUCT_UPLOAD_IMG
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.response.market.MarketDetailResponse
import io.sinzak.android.remote.retrofit.CallImpl
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MarketProductModel @Inject constructor() : BaseModel() {

    val art = MutableStateFlow<MarketDetailResponse.Detail?>(null)

    fun loadProduct(id : String){
        art.value = null
        CallImpl(API_GET_PRODUCT_DETAIL, this,paramStr0 = id).apply{
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
        }
    }
}