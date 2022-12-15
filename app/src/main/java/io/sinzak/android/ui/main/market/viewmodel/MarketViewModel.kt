package io.sinzak.android.ui.main.market.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.insets.SoftKeyModel
import io.sinzak.android.model.market.MarketArtModel
import io.sinzak.android.model.market.MarketHistoryModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(val soft : SoftKeyModel, val marketArtModel: MarketArtModel, val marketHistory: MarketHistoryModel) : BaseViewModel() {

    private val _searchPageOn = MutableStateFlow(false)
    val searchPageOn : StateFlow<Boolean> get() = _searchPageOn
    private val _searchHistoryOn = MutableStateFlow(false)
    val searchHistoryOn : StateFlow<Boolean> get() = _searchHistoryOn


    private val _searchFieldText = MutableStateFlow("")
    val searchFieldText : StateFlow<String> get() = _searchFieldText

    fun openSearchPage(){
        _searchPageOn.value = true
        _searchHistoryOn.value = true
    }

    fun typeSearchFieldText(cs : CharSequence){
        cs.toString().let{
            if(_searchFieldText.value != it)
            {
                _searchFieldText.value = it
            }
        }
    }

    fun deleteSearchField(){
        _searchFieldText.value = ""
    }

    fun searchText(){
        searchFieldText.value.let{
            keyword->
            if(keyword.isEmpty())
                return

            search(keyword)

        }
    }

    fun search(tag : String)
    {
        marketHistory.putHistory(tag)
        _searchHistoryOn.value = false
    }





    fun closeSearchPage(){

        if(searchHistoryOn.value)
        {
            _searchHistoryOn.value = false
            if(searchFieldText.value.isBlank())
            {
                _searchPageOn.value = false
            }
        }
        else if(searchPageOn.value)
            _searchPageOn.value = false
        soft.hideKeyboard()
    }
}