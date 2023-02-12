package io.sinzak.android.ui.main.profile.art

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.art.adapter.SaleWorkAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SaleViewModel @Inject constructor(
    private val model : ProfileModel,
    private val productModel : ProductDetailModel
) : BaseViewModel() {

    /**
     * 완료된 아이템을 보여줄건지?
     */
    val isCompleteList = MutableStateFlow(false)

    val adapter = SaleWorkAdapter(productModel::endTrade){ a->
        productModel.loadProduct(a.id!!)
        navigation.changePage(Page.ART_DETAIL)
    }.apply {
        model.productList.onEach {
            invokeBooleanFlow(
                isCompleteList,
                {
                    setArts(it.filter { !it.complete!! })
                },
                {
                    setArts(it.filter { it.complete!! })
                }
            )
        }.launchIn(viewModelScope)
    }


    fun setIsComplete(status : Boolean)
    {
        isCompleteList.value = status
    }



}