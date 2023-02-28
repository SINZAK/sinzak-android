package io.sinzak.android.ui.main.market.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.MarketArtModel
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.market.adapter.ArtProductAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ArtsViewModel @Inject constructor(
    val md: MarketArtModel,
    val productModel: ProductDetailModel
) : BaseViewModel() {

    val isProductEmpty = MutableStateFlow(false)

    val adapter = ArtProductAdapter(productModel::postProductLike) { p ->
        productModel.loadProduct(p.id!!)
        navigation.changePage(Page.ART_DETAIL)
    }.apply {
        md.marketProducts.onEach {
            setProducts(it) { bool ->
                isProductEmpty.value = bool
            }
        }.launchIn(viewModelScope)
    }


    val stShowOnSale: StateFlow<Boolean> get() = md.stShowOnSale


    fun toggleShowOnSale() {
        md.onClickShowOnSale(!stShowOnSale.value)
    }


}