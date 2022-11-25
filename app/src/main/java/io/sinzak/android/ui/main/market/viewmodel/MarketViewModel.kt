package io.sinzak.android.ui.main.market.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.insets.SoftKeyModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MarketViewModel @Inject constructor(val soft : SoftKeyModel) : BaseViewModel() {

    private val _searchPageOn = MutableStateFlow(false)
    val searchPageOn : StateFlow<Boolean> get() = _searchPageOn


    private val _searchFieldText = MutableStateFlow("")
    val searchFieldTExt : StateFlow<String> get() = _searchFieldText

    fun openSearchPage(){
        _searchPageOn.value = true
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





    @Inject
    lateinit var softKeyModel: SoftKeyModel

    fun closeSearchPage(){
        _searchPageOn.value = false
        softKeyModel.hideKeyboard()
    }
}