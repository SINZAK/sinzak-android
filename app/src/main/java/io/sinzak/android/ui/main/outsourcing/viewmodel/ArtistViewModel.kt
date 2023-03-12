package io.sinzak.android.ui.main.outsourcing.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.model.works.WorkListModel
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.system.LogDebug
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.market.adapter.ArtsAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    val model : WorkListModel,
    val pModel : ProductDetailModel
) : BaseViewModel() {

    private val arts = mutableListOf<Product>()

    val isArtsNull = MutableStateFlow(false)

    val adapter = ArtsAdapter(arts){
        pModel.loadWork(it.id!!)
        useFlag(pModel.productLoadSuccessFlag){
            navigation.changePage(Page.ART_DETAIL)
        }
    }

    private fun updateProducts(p : MutableList<Product>){
        isArtsNull.value = p.isEmpty()
        arts.clear()
        arts.addAll(p)
        adapter.notifyDataSetChanged()
    }


    fun getMoreWorks(){
        LogDebug(javaClass.name, "맨 밑 도달해서 요청")
        model.getRemoteMarketWorks(refresh = false)
    }

    init{
        model.workList.onEach(::updateProducts).launchIn(viewModelScope)
    }


}