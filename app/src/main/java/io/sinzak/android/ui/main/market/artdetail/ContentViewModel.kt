package io.sinzak.android.ui.main.market.artdetail

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.market.MarketProductModel
import io.sinzak.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ContentViewModel @Inject constructor(
    val model : MarketProductModel
): BaseViewModel(){

    val art get() = model.art

    val imgAdapter = VpAdapter()

    init{

        invokeStateFlow(model.art){art->
            art?.let{
                imgAdapter.imgs = it.imgUrls
                imgAdapter.notifyDataSetChanged()
            }

        }
    }

}