package io.sinzak.android.ui.main.outsourcing.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Sort
import io.sinzak.android.model.works.WorkListModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.outsourcing.WorkConnect
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class OutsourcingViewModel @Inject constructor(
    val model : WorkListModel,
    val connect : WorkConnect
) : BaseViewModel() {

    val isClientList = MutableStateFlow(true)

    val sortOrder = MutableStateFlow(Sort.BY_REFER)



    private fun setSortOrder(order : Sort){
        sortOrder.value = order
        model.getRemoteMarketWorks(refresh = true, sort = order)
    }


    fun setIsClient(status : Boolean){
        isClientList.value = status
        model.getRemoteMarketWorks(refresh = true, isCustomer = status)
    }



    fun showSortBottom(){

        connect.showSortDialog(sortOrder.value){
            setSortOrder(it)
        }

    }


    init{

        model.getRemoteMarketWorks(true, sort = Sort.BY_RECENT, search = "")

    }


}