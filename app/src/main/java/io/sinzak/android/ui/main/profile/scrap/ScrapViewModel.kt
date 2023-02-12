package io.sinzak.android.ui.main.profile.scrap

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.scrap.adapter.ScrapAdapter
import kotlinx.coroutines.flow.MutableStateFlow
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

    val adapter = ScrapAdapter(::onItemClick)
        .apply {
            model.productWishList.onEach {
                invokeBooleanFlow(
                    showOnSale,
                    {
                        setScraps(it.filter { !it.complete!! })
                    },
                    {
                        setScraps(it)
                    }
                )
            }
        }

    /*********************************************************************
     * Click Event
     **********************************************************************/

    private fun onItemClick(product : Product)
    {
        productModel.loadProduct(product.id!!)
        navigation.changePage(Page.ART_DETAIL)
    }

    fun toggleShowOnSale()
    {
        showOnSale.value = !showOnSale.value
    }

    fun backPressed()
    {
        navigation.revealHistory()
    }
}