package io.sinzak.android.ui.main.market.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.enums.Page
import io.sinzak.android.model.insets.SoftKeyModel
import io.sinzak.android.model.market.MarketArtModel
import io.sinzak.android.model.market.MarketHistoryModel
import io.sinzak.android.model.market.MarketWriteModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(
    val soft: SoftKeyModel,
    val marketArtModel: MarketArtModel,
    val marketHistory: MarketHistoryModel,
    val writeModel: MarketWriteModel
) : BaseViewModel() {

    private val _searchPageOn = MutableStateFlow(false)
    val searchPageOn: StateFlow<Boolean> get() = _searchPageOn
    private val _searchHistoryOn = MutableStateFlow(false)
    val searchHistoryOn: StateFlow<Boolean> get() = _searchHistoryOn


    private val _searchFieldText = MutableStateFlow("")
    val searchFieldText: StateFlow<String> get() = _searchFieldText


    /********************************
     * LOCAL VARIABLE CHANGE
     ********************************/

    fun openSearchPage() {
        marketHistory.getRemoteHistoryList()
        _searchPageOn.value = true
        _searchHistoryOn.value = true
    }

    fun typeSearchFieldText(cs: CharSequence) {
        cs.toString().let {
            if (_searchFieldText.value != it) {
                _searchFieldText.value = it
            }
        }
    }

    fun showHistory(){
        marketHistory.getRemoteHistoryList()
        _searchHistoryOn.value = true
    }

    fun deleteSearchField() {
        _searchFieldText.value = ""
        marketArtModel.getRemoteMarketProducts(refresh = true, search = "")
    }


    /********************************
     * REQUEST
     ********************************/


    fun searchText() {
        searchFieldText.value.let { keyword ->
            if (keyword.isEmpty())
                return

            search(keyword)

        }
    }

    fun search(tag: String) {
        if (tag.length < 2){
            uiModel.showToast(valueModel.getString(R.string.str_search_length))
            return
        }
        marketArtModel.getRemoteMarketProducts(refresh = true, search = tag)
        soft.hideKeyboard()
        _searchFieldText.value = tag
        _searchHistoryOn.value = false
    }

    fun getMarketProductRemote(refresh: Boolean) = marketArtModel.getRemoteMarketProducts(refresh)


    /********************************
     * PAGE TRANSACTION
     ********************************/

    fun gotoBuildPage() {
        writeModel.setProductType(0)
        writeModel.startBuild()
        navigation.changePage(Page.NEW_POST)
    }


    fun closeSearchPage() {

        if (searchHistoryOn.value) {
            _searchHistoryOn.value = false
            if (searchFieldText.value.isBlank()) {
                _searchPageOn.value = false
            }
        } else if (searchPageOn.value)
            _searchPageOn.value = false

        if(!searchPageOn.value)
            marketArtModel.getRemoteMarketProducts(refresh = true)

        soft.hideKeyboard()
    }
}