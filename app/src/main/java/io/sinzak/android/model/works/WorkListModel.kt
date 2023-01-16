package io.sinzak.android.model.works

import io.sinzak.android.constants.API_GET_MARKET_WORKS
import io.sinzak.android.enums.Sort
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.remote.dataclass.response.market.MarketProductResponse
import io.sinzak.android.remote.retrofit.CallImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WorkListModel @Inject constructor() : BaseModel() {

    private val _worksList = MutableStateFlow(mutableListOf<Product>())
    val workList : StateFlow<MutableList<Product>> get() = _worksList








    private var maxPage = 9999
    private var currentPage = 0
    private var searchKeyword = ""
    private var categoryString = ""
    private var sort : Sort = Sort.BY_RECENT
    private var isCustomer = true



    /************************************************************************
     * DATA INSERTING
     *************************************************************************/













    /************************************************************************
     * REMOTE DATASOURCE
     *************************************************************************/

    fun getRemoteMarketWorks(refresh : Boolean = false, category : String = "", sort : Sort? = null, isCustomer : Boolean? = null, search : String = "")
    {
        val pageSize = 10

        val page = if(refresh) {
            _worksList.value = mutableListOf()
            maxPage = 9999

            when {
                category.isNotBlank() ->
                    categoryString = category
                sort != null ->
                    this.sort = sort
                isCustomer != null ->
                    this.isCustomer = isCustomer
                search.isNotBlank() ->
                    this.searchKeyword = search
                else ->{
                    this.searchKeyword = ""
                }

            }




            searchKeyword = search
            0
        }else currentPage + 1

        if(page > maxPage)
            return

        val align = when(this.sort){
            Sort.BY_REFER -> "recommend"
            Sort.BY_FAME -> "popular"
            Sort.BY_RECENT -> "recent"
            Sort.BY_HIGHPRICE -> "high"
            Sort.BY_LOWPRICE -> "low"
        }

        CallImpl(
            API_GET_MARKET_WORKS,
            this,
            paramInt0 = page,
            paramInt1 = pageSize,
            paramStr0 = align,
            paramStr1 = categoryString,
            paramStr2 = searchKeyword,
            paramBool0 = this.isCustomer

        ).apply{
            remote.sendRequestApi(this)
        }


    }






    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }







    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api){
            API_GET_MARKET_WORKS ->{
                if(body.success == false)
                    return
                body as MarketProductResponse
                val list = mutableListOf<Product>()
                list.addAll(_worksList.value)
                list.addAll(body.products ?: listOf())
                _worksList.value = list.distinct().toMutableList()
            }
        }
    }
}