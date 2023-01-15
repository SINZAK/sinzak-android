package io.sinzak.android.ui.main.home.more

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.enums.HomeMore.*
import io.sinzak.android.model.market.HomeProductModel
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.home.adapter.ArtVerticalAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeMoreViewModel  @Inject constructor(
    val model : HomeProductModel,
    val detailModel : ProductDetailModel
): BaseViewModel() {


    val products = mutableListOf<Product>()


    val title = MutableStateFlow("")

    val adapter = ArtVerticalAdapter(products){
        detailModel.loadProduct(it.id!!)
        uiModel.openProductDetail()
    }


    init{
        when(model.morePageType.value){
            REFER -> {
                model.getMoreRefer()
                invokeStateFlow(model.referProductsAll, ::updateProducts)
                title.value = valueModel.getString(R.string.str_home_more_refer)
            }
            FOLLOWING -> {
                model.getMoreFollowing()
                invokeStateFlow(model.followingProductsAll, ::updateProducts)
                title.value = valueModel.getString(R.string.str_home_more_following)
            }
            RECENT -> {
                invokeStateFlow(model.recentProductsAll, ::updateProducts)
                title.value = valueModel.getString(R.string.str_home_recent)

            }
        }

    }

    fun updateProducts(products : List<Product>){
        this.products.addAll(
            products.filter{
                it !in this.products
            }
        )
        adapter.notifyDataSetChanged()
    }

}