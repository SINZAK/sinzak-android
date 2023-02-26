package io.sinzak.android.ui.main.profile.scrap


import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.scrap.adapter.ScrapAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ScrapViewModel @Inject constructor(
    val model : ProfileModel,
    val productModel : ProductDetailModel
) : BaseViewModel() {

    /**
     * 스크랩 타입 : 판매작품:false , 의뢰해요:true
     */
    val scrapType = MutableStateFlow(PRODUCT)

    /**
     * 판매중인 작품인가?
     */
    val showOnSale = MutableStateFlow(false)

    /**
     * 없어요 이미지를 보여줄것인가?
     */
    val showNothing = MutableStateFlow(false)


    /**
     * 스크랩 목록 어뎁터
     */
    val adapter = ScrapAdapter(::onItemClick)
        .apply {typeFlow(this)}

    private fun typeFlow(adapter: ScrapAdapter)
    {
        invokeBooleanFlow(
            scrapType,
            {onSaleFlow(adapter,model.productWishList)},
            {onSaleFlow(adapter,model.workWishList)}
        )
    }

    private fun onSaleFlow(adapter: ScrapAdapter, scrapList : StateFlow<MutableList<Product>>)
    {
        invokeBooleanFlow(
            showOnSale,
            {setScrapData(adapter, scrapList)},
            {setScrapFilterData(adapter, scrapList)}
        )
    }

    private fun setScrapData(adapter: ScrapAdapter, scrapList : StateFlow<MutableList<Product>>)
    {
        scrapList.onEach { adapter.setScraps(it) }.launchIn(viewModelScope)
        showNothing.value = adapter.itemCount > 0
    }

    private fun setScrapFilterData(adapter: ScrapAdapter, scrapList : StateFlow<MutableList<Product>>)
    {
        scrapList.onEach { list-> adapter.setScraps(list.filter { it.complete == false }) }.launchIn(viewModelScope)
        showNothing.value = adapter.itemCount > 0
    }


    /*********************************************************************
     * Click Event
     **********************************************************************/

    /**
     * 스크랩 타입을 클릭
     */
    fun changeType(type : Boolean)
    {
        scrapType.value = type
    }

    /**
     * 개별 아이템을 클릭
     */
    private fun onItemClick(product : Product)
    {
        if (scrapType.value == PRODUCT) productModel.loadProduct(product.id!!)
        else productModel.loadWork(product.id!!)

        navigation.changePage(Page.ART_DETAIL)
    }

    /**
     * 판매중 작품을 클릭
     */
    fun toggleShowOnSale()
    {
        showOnSale.value = !showOnSale.value
    }

    /**
     * 뒤로 가기 클릭
     */
    fun backPressed()
    {
        navigation.revealHistory()
    }

    companion object {
        const val PRODUCT = false
        const val WORK = true
    }
}