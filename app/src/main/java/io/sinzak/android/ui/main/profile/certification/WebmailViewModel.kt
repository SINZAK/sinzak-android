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

    private val _isUpload = MutableStateFlow(false)
    val isUpload : StateFlow<Boolean> get() = _isUpload

    //인증 방식 선택
    fun changePage(page: Int){
        _currentPage.value = page
    }


    //학교 웹메일 입력란
    fun mailInputText(cs : CharSequence) {
        cs.toString().let {
            if(_webMailInput.value != it){
                _webMailInput.value = it
            }
        }
    }

    //학교 웹메일 텍스트 삭제
    fun deleteMailInput() {
        _webMailInput.value = ""
    }

    //코드 입력란
    fun codeInputText(cs : CharSequence) {
        cs.toString().let {
            if(_codeInput.value != it){
                _codeInput.value = it
            }
        }
    }

    //코드 텍스트 삭제
    fun deleteCodeInput() {
        _codeInput.value = ""
    }

    //메일 전송하기
    fun sendCodeToMail(){
        _isCodeSend.value = true
    }

    //사진 업로드하기
    fun upLoadImg(){
        _isUpload.value = true
    }

    //선택한 사진 취소하기
    fun deleteSelectImg(){
        _isUpload.value = false
    }

    fun submitWithoutEmail(){
        //signModel.join()
    }

}