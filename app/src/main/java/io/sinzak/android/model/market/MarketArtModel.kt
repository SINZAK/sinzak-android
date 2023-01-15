package io.sinzak.android.model.market

import io.sinzak.android.constants.API_GET_MARKET_PRODUCTS
import io.sinzak.android.enums.Sort
import io.sinzak.android.enums.Sort.*
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.remote.dataclass.response.market.MarketProductResponse
import io.sinzak.android.remote.retrofit.CallImpl
import io.sinzak.android.system.LogDebug
import io.sinzak.android.ui.main.search.HistoryViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MarketArtModel @Inject constructor() : BaseModel() {


    private val _stShowOnSale = MutableStateFlow(false)
    val stShowOnSale : StateFlow<Boolean> get() = _stShowOnSale

    private val _sortOrder = MutableStateFlow(BY_REFER)
    val sortOrder: StateFlow<Sort> get() = _sortOrder



    private val _marketProducts = MutableStateFlow(mutableListOf<Product>())
    val marketProducts : StateFlow<List<Product>> get() = _marketProducts
    private var currentPage = 0
    private var maxPage = 9999

    private var categoryString = ""

    private var searchKeyword = ""

    fun setCategoryString(str : String){
        categoryString = str
        getRemoteMarketProducts(refresh = true)
    }
    fun getCategoryString() : String{
        return categoryString
    }

    fun setMarketSort(sort: Sort)
    {
        _sortOrder.value = sort
        getRemoteMarketProducts(refresh = true)
    }


    fun onClickShowOnSale(status : Boolean)
    {
        _stShowOnSale.value = !status
    }


    fun getRemoteMarketProducts(refresh : Boolean = false){
        getRemoteMarketProducts(refresh,"")
    }

    fun getRemoteMarketProducts(refresh : Boolean = false, search : String = "")
    {
        val pageSize = 10

        val page = if(refresh) {
            _marketProducts.value = mutableListOf()
            maxPage = 9999
            searchKeyword = search
            0
        }else currentPage + 1

        if(page > maxPage)
            return

        val align = when(sortOrder.value){
            BY_REFER -> "recommend"
            BY_FAME -> "popular"
            BY_RECENT -> "recent"
            BY_HIGHPRICE -> "high"
            BY_LOWPRICE -> "low"
        }
        val category = categoryString

        CallImpl(
            API_GET_MARKET_PRODUCTS,
            this,
            paramInt0 = page,
            paramInt1 = pageSize,
            paramStr0 = align,
            paramStr1 = category,
            paramStr2 = searchKeyword,
            paramBool0 = stShowOnSale.value

        ).apply{
            remote.sendRequestApi(this)
        }


    }


    fun onMarketProductResponse(response : MarketProductResponse)
    {
        response.pageable?.pageNumber?.let{
            currentPage = it
        }
        response.totalPage?.let{
            maxPage = it
        }
        response.products?.let{products->
            val list = mutableListOf<Product>()
            list.addAll(_marketProducts.value)
            list.addAll(products)

            _marketProducts.value = list.distinctBy { it.id }.toMutableList()

            LogDebug(javaClass.name,"[MARKET VIEWMODEL] 현재 아이템 ${list.size} ")

        }
    }



    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }

    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api)
        {
            API_GET_MARKET_PRODUCTS ->{
                onMarketProductResponse(body as MarketProductResponse)
            }
        }
    }



}