package io.sinzak.android.ui.main.home.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.market.HomeProductModel
import io.sinzak.android.model.market.MarketProductModel
import io.sinzak.android.ui.base.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    val model : HomeProductModel
) : BaseViewModel() {


    fun getProducts(){
        model.getProducts()
    }
}