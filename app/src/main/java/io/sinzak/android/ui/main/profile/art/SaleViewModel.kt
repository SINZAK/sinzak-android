package io.sinzak.android.ui.main.profile.art

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.ui.main.profile.art.adapter.SaleWorkAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SaleViewModel @Inject constructor(
    val model : ProfileModel,
    private val productModel : ProductDetailModel
) : ProfileArtViewModel() {

    val adapter = SaleWorkAdapter(
        ::onEndTrade,
        ::onItemClick,
        completeList,
        viewType = 0,
        PRODUCT,
        model
    )

    init {
        settingAdapter(adapter,model.productList)
    }

    override fun onItemClick(product: Product) {
        productModel.loadProduct(product.id!!)
        useFlag(productModel.productLoadSuccessFlag){
            navigation.changePage(Page.ART_DETAIL)
        }
    }

    override fun onEndTrade(id: Int, callback: () -> Unit) {
        if (completeList.value) return
        productModel.updateProductState(id, PRODUCT)
        useFlag(productModel.productStatusUpdateFlag){
            callback()
        }
    }

}