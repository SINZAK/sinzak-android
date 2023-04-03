package io.sinzak.android.model.market

import io.sinzak.android.constants.*
import io.sinzak.android.enums.HomeMore
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.local.BannerData
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.remote.dataclass.response.home.BannerResponse
import io.sinzak.android.remote.dataclass.response.market.HomeMoreResponse
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
    private val _recommendProducts  = MutableStateFlow(listOf<Product>())
    private val _followingProducts  = MutableStateFlow(listOf<Product>())

    val hotProducts : StateFlow<List<Product>> get() = _hotProducts
    val newProducts : StateFlow<List<Product>> get() = _newProducts
    val tradingProducts : StateFlow<List<Product>> get() = _tradingProducts
    val recommendProducts : StateFlow<List<Product>> get() = _recommendProducts
    val followingProducts : StateFlow<List<Product>> get() = _followingProducts


    val referProductsAll = MutableStateFlow(listOf<Product>())
    val followingProductsAll = MutableStateFlow(listOf<Product>())


    val banners = MutableStateFlow(listOf<BannerData>())


    val noti = MutableStateFlow(0)


    val morePageType = MutableStateFlow(HomeMore.REFER)


    fun getProducts(){
        remote.sendRequestApi(
            CallImpl(API_GET_HOME_PRODUCTS, this)
        )
    }

    fun getMoreRefer(){
        remote.sendRequestApi(
            CallImpl(API_GET_HOME_REFER, this)
        )
    }

    fun getMoreFollowing(){
        remote.sendRequestApi(
            CallImpl(API_GET_HOME_FOLLOWING, this)
        )
    }

    fun getBanner(){
        banners.value = listOf()
        remote.sendRequestApi(
            CallImpl(API_GET_HOME_BANNER, this)
        )
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }

    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api)
        {
            API_GET_HOME_PRODUCTS ->{
                body as MarketHomeResponse


                noti.value = noti.value + 1

                body.data?:run{
                    return
                }


                _hotProducts.value = listOf()
                _tradingProducts.value = listOf()
                _newProducts.value = listOf()
                _followingProducts.value = listOf()
                _recommendProducts.value = listOf()


                _hotProducts.value = body.data.hotProducts?.toMutableList() ?: listOf()
                _newProducts.value = body.data.newProducts?.toMutableList() ?: listOf()
                _tradingProducts.value = body.data.tradingProducts?.toMutableList() ?: listOf()
                _recommendProducts.value = body.data.recommends?.toMutableList() ?: listOf()
                _followingProducts.value = body.data.followingProducts?.toMutableList() ?: listOf()


            }

            API_GET_HOME_BANNER ->{
                body as BannerResponse
                banners.value = body.banners
            }

            API_GET_HOME_REFER ->{
                body as HomeMoreResponse
                referProductsAll.value = body.products ?: listOf()
            }

            API_GET_HOME_FOLLOWING ->{
                body as HomeMoreResponse
                followingProductsAll.value = body.products ?: listOf()
            }

        }
    }
}