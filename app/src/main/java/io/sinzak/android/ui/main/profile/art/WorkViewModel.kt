package io.sinzak.android.ui.main.profile.art

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.ui.main.profile.art.adapter.SaleWorkAdapter
import javax.inject.Inject

@HiltViewModel
class WorkViewModel @Inject constructor(
    val model : ProfileModel,
    private val productModel: ProductDetailModel
) : ProfileArtViewModel() {

    val adapter = SaleWorkAdapter(
        ::onEndTrade,
        ::onItemClick,
        completeList,
        viewType = 1,
        WORK,
        model
    )

    init {
        settingAdapter(adapter,model.workList)
    }

    override fun onItemClick(product: Product) {
        productModel.loadWork(product.id!!)
        useFlag(productModel.productLoadSuccessFlag){
            navigation.changePage(Page.ART_DETAIL)
        }

    }

    override fun onEndTrade(id: Int,callback : ()->Unit) {
        if (completeList.value) return
        productModel.updateProductState(id, WORK)
        useFlag(productModel.productStatusUpdateFlag){
            callback()
        }
    }


}