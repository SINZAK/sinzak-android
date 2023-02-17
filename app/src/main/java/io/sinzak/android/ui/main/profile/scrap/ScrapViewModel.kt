package io.sinzak.android.ui.main.profile.scrap

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.scrap.adapter.ScrapAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ScrapViewModel @Inject constructor(
    val model : ProfileModel,
    val productModel : ProductDetailModel
) : BaseViewModel() {

    /**
     * 판매중인 작품인가?
     */
    val showOnSale = MutableStateFlow(false)

    /**
     * 상태에 따른 스크랩 목록을 저장하는 공간
     */
    private val scraps = mutableListOf<Product>()

    /**
     * 스크랩 목록 어뎁터
     */
    val adapter = ScrapAdapter(scraps,::onItemClick)


    init {
        setScrapData()
    }

    /**
     * 상태에 따라 스크랩 목록을 업데이트합니다
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun updateScraps(scrapList : MutableList<Product>)
    {
        scraps.clear()
        if (showOnSale.value){
            scraps.addAll(scrapList.filter { it.complete == false })
        }
        else scraps.addAll(scrapList)

        adapter.notifyDataSetChanged()
    }

    /**
     * 모델에서 스크랩목록을 가져옵니다
     */
    private fun setScrapData()
    {
        model.productWishList.onEach(::updateScraps).launchIn(viewModelScope)
    }

    /*********************************************************************
     * Click Event
     **********************************************************************/

    /**
     * 개별 아이템을 클릭
     */
    private fun onItemClick(product : Product)
    {
        productModel.loadProduct(product.id!!)
        navigation.changePage(Page.ART_DETAIL)
    }

    /**
     * 판매중 작품을 클릭
     */
    fun toggleShowOnSale()
    {
        showOnSale.value = !showOnSale.value
        setScrapData()
    }

    /**
     * 뒤로 가기 클릭
     */
    fun backPressed()
    {
        navigation.revealHistory()
    }
}