package io.sinzak.android.ui.main.profile.art

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.ui.main.profile.art.adapter.SaleWorkAdapter
import javax.inject.Inject

@HiltViewModel
class RequestViewModel @Inject constructor(
    private val model : ProfileModel,
    private val productModel : ProductDetailModel
) : ProfileArtViewModel() {

    init {

        adapter = SaleWorkAdapter(
            productModel::endTrade,
            ::onItemClick,
            completeList.value,
            viewType = 2
        )

        settingAdapter(model.workEmployList)
    }

    override fun onItemClick(product: Product) {
        productModel.loadWork(product.id!!)
        navigation.changePage(Page.ART_DETAIL)
    }

}