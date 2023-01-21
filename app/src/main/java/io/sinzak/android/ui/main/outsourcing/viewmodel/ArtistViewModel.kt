package io.sinzak.android.ui.main.outsourcing.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.model.works.WorkListModel
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.market.adapter.ArtsAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    val model : WorkListModel,
    val pModel : ProductDetailModel
) : BaseViewModel() {

    private val arts = mutableListOf<Product>()
    val adapter = ArtsAdapter(arts){
        pModel.loadWork(it.id!!)
        navigation.changePage(Page.ART_DETAIL)
    }





    private fun updateProducts(p : MutableList<Product>){
        arts.clear()
        arts.addAll(p)
        adapter.notifyDataSetChanged()
    }


    fun getMoreWorks(){
        model.getRemoteMarketWorks(refresh = false)
    }

    init{
        model.workList.onEach(::updateProducts).launchIn(viewModelScope)
    }


}