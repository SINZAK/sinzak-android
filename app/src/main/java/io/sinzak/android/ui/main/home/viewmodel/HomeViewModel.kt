package io.sinzak.android.ui.main.home.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.HomeProductModel
import io.sinzak.android.ui.base.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    val model : HomeProductModel
) : BaseViewModel() {


    val isLogin get() = signModel.isLogin

    fun getProducts(){
        model.getProducts()
        model.getBanner()
    }

    fun gotoNotification(){
        if (!signModel.isUserLogin()) return
        navigation.changePage(Page.HOME_NOTIFICATION)
    }
}