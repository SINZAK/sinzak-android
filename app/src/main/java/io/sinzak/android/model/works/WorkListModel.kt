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


    /**
     * Works 리스트 불러오기
     * refresh : true - 리스트 초기화 및 파라미터 수신, false - 페이징
     * category : 카테고리 텍스트, 콤마로 구분하여 3개까지
     * sort : 정렬 순서
     * isCustomer : true - 구매합니다 false - 판매합니다ㅣ
     * search : 검색어 키워드
     *
     * 각 항목이 null 이면 저장된 값 사용
     * 항목 하나씩만 변경 가능
     */
    fun getRemoteMarketWorks(refresh : Boolean = false, category : String? = null, sort : Sort? = null, isCustomer : Boolean? = null, search : String? = null)
    {
        val pageSize = 10

        val page = if(refresh) {
            _worksList.value = mutableListOf()
            maxPage = 9999

            category?.let{
                categoryString = it
            }
            sort?.let{
                this.sort = sort
            }
            isCustomer?.let{
                this.isCustomer = isCustomer
            }
            search?.let{
                searchKeyword = search
            }
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