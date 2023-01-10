package io.sinzak.android.model.market

import com.google.gson.annotations.SerializedName
import io.sinzak.android.constants.API_GET_HOME_PRODUCTS
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.remote.dataclass.response.market.MarketHomeResponse
import io.sinzak.android.remote.retrofit.CallImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeProductModel @Inject constructor() : BaseModel() {
    private val _hotProducts = MutableStateFlow(listOf<Product>())
    private val _newProducts = MutableStateFlow(listOf<Product>())
    private val _tradingProducts = MutableStateFlow(listOf<Product>())
    val hotProducts : StateFlow<List<Product>> get() = _hotProducts
    val newProducts : StateFlow<List<Product>> get() = _newProducts
    val tradingProducts : StateFlow<List<Product>> get() = _tradingProducts



    fun getProducts(){
        remote.sendRequestApi(
            CallImpl(API_GET_HOME_PRODUCTS, this)
        )
    }


    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }

    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api)
        {
            API_GET_HOME_PRODUCTS ->{
                body as MarketHomeResponse

                _hotProducts.value = body.hotProducts ?: listOf()
                _newProducts.value = body.newProducts ?: listOf()
                _tradingProducts.value = body.tradingProducts ?: listOf()

            }
        }
    }
}