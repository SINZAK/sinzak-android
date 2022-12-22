package io.sinzak.android.ui.main.profile.certification

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class WebmailViewModel @Inject constructor() : BaseViewModel() {

    private val _webMailInput = MutableStateFlow("")
    val  webMailInput : StateFlow<String> get() = _webMailInput


    fun typeInputText(cs : CharSequence) {
        cs.toString().let {
            if(_webMailInput.value != it){
                _webMailInput.value = it
            }
        }
    }

    fun deleteInput() {
        _webMailInput.value = ""
    }

}