package io.sinzak.android.ui.main.outsourcing.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.enums.Page
import io.sinzak.android.enums.Sort
import io.sinzak.android.model.insets.SoftKeyModel
import io.sinzak.android.model.market.MarketHistoryModel
import io.sinzak.android.model.market.MarketWriteModel
import io.sinzak.android.model.works.WorkListModel
import io.sinzak.android.system.LogDebug
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
        val buildType = if (isClientList.value) 1 else 2
        writeModel.setProductType(buildType)
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

        if(!searchOn.value) {
            LogDebug(javaClass.name, "검색창 뒤로가기 때 요청")
            model.getRemoteMarketWorks(refresh = true, search = "")
        }

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
        searchFieldText.value.let { keyword ->
            if (keyword.isEmpty())
                return

            search(keyword)

        }
    }

    fun search(tag: String){
        if (tag.length < 2){
            uiModel.showToast(valueModel.getString(R.string.str_search_length))
            return
        }
        LogDebug(javaClass.name,"검색에서 보냄")
        model.getRemoteMarketWorks(refresh = true, search = tag)
        soft.hideKeyboard()
        searchFieldText.value = tag
        historyOn.value = false
    }

    fun deleteSearchField(){
        searchFieldText.value = ""
    }


    private fun setSortOrder(order : Sort){
        sortOrder.value = order
        LogDebug(javaClass.name,"sort 변경시 요청")
        model.getRemoteMarketWorks(refresh = true, sort = order)
    }


    fun setIsClient(status : Boolean){
        isClientList.value = status
        LogDebug(javaClass.name,"작업해요/의뢰해요 변경시 요청")
        model.getRemoteMarketWorks(refresh = true, isCustomer = status)
    }



    fun showSortBottom(){

        connect.showSortDialog(sortOrder.value){
            setSortOrder(it)
        }

    }


    init{

        LogDebug(javaClass.name,"뷰모델 init시 요청")
        model.getRemoteMarketWorks(true, sort = Sort.BY_REFER, search = "")

    }


}