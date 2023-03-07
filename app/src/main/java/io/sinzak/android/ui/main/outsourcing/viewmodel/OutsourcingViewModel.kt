package io.sinzak.android.ui.main.outsourcing.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.enums.Sort
import io.sinzak.android.model.insets.SoftKeyModel
import io.sinzak.android.model.market.MarketHistoryModel
import io.sinzak.android.model.market.MarketWriteModel
import io.sinzak.android.model.works.WorkListModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.outsourcing.WorkConnect
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class OutsourcingViewModel @Inject constructor(
    val model : WorkListModel,
    val writeModel: MarketWriteModel,
    val connect : WorkConnect,
    val historyModel: MarketHistoryModel,
    val soft : SoftKeyModel
) : BaseViewModel() {

    val isClientList = MutableStateFlow(true)

    val searchOn = MutableStateFlow(false)
    val historyOn = MutableStateFlow(false)
    val searchFieldText = MutableStateFlow("")

    val sortOrder = MutableStateFlow(Sort.BY_REFER)

    fun openSearch(){
        historyModel.getRemoteHistoryList()
        searchOn.value = true
        historyOn.value = true
    }


    fun gotoBuildPage() {
        writeModel.startBuild()
        navigation.changePage(Page.NEW_POST)
    }

    fun closeSearchPage() {

        if (historyOn.value) {
            historyOn.value = false
            if (searchFieldText.value.isBlank()) {
                searchOn.value = false
            }
        } else if (searchOn.value)
            searchOn.value = false

        if(!searchOn.value)
            model.getRemoteMarketWorks(refresh = true, search = "")

        soft.hideKeyboard()
    }


    fun showHistory(){
        historyModel.getRemoteHistoryList()
        historyOn.value = true
    }

    fun typeSearchFieldText(cs : CharSequence){
        searchFieldText.value = cs.toString()
    }

    fun searchText(){
        historyModel.getRemoteHistoryList()
        historyOn.value = false
        model.getRemoteMarketWorks(refresh = true, search = searchFieldText.value)

    }

    fun deleteSearchField(){
        searchFieldText.value = ""
    }


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

    fun setNullImg(isArtsNull : Boolean) : Boolean {
        if (searchOn.value)
        {
            return isArtsNull
        }
        return false
    }


    init{

        model.getRemoteMarketWorks(true, sort = Sort.BY_RECENT, search = "")

    }


}