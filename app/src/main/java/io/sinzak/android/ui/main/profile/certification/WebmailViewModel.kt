package io.sinzak.android.ui.main.profile.certification

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.context.SignModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class WebmailViewModel @Inject constructor(
    val signModel: SignModel
) : BaseViewModel() {

    private val _currentPage = MutableStateFlow(0)
    val currentPage : StateFlow<Int> get() = _currentPage

    private val _webMailInput = MutableStateFlow("")
    val  webMailInput : StateFlow<String> get() = _webMailInput

    private val _codeInput = MutableStateFlow("")
    val codeInput : StateFlow<String> get() = _codeInput

    private val _isCodeSend = MutableStateFlow(false)
    val isCodeSend : StateFlow<Boolean> get() = _isCodeSend

    fun changePage(page: Int){
        _currentPage.value = page
    }


    fun mailInputText(cs : CharSequence) {
        cs.toString().let {
            if(_webMailInput.value != it){
                _webMailInput.value = it
            }
        }
    }

    fun deleteMailInput() {
        _webMailInput.value = ""
    }

    fun codeInputText(cs : CharSequence) {
        cs.toString().let {
            if(_codeInput.value != it){
                _codeInput.value = it
            }
        }
    }

    fun deleteCodeInput() {
        _codeInput.value = ""
    }

    fun sendCodeToMail(){
        _isCodeSend.value = true
    }

    fun submitWithoutEmail(){
        //signModel.join()
    }

}