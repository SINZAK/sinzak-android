package io.sinzak.android.ui.main.profile.certification

import android.text.TextUtils
import android.util.Patterns
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

    private val _webMailState = MutableStateFlow(0)
    val webMailState : StateFlow<Int> get() = _webMailState

    private val _codeInput = MutableStateFlow("")
    val codeInput : StateFlow<String> get() = _codeInput

    private val _codeState = MutableStateFlow(0)
    val codeState : StateFlow<Int> get() = _codeState

    private val _isUpload = MutableStateFlow(false)
    val isUpload : StateFlow<Boolean> get() = _isUpload


    //인증 방식 선택
    fun changePage(page: Int){
        _currentPage.value = page
    }


    //학교 웹메일 입력란
    fun mailInputText(cs : CharSequence) {
        cs.toString().let { text ->
            if(_webMailInput.value != text){
                _webMailInput.value = text
            }
            if (!TextUtils.isEmpty(text) && Patterns.EMAIL_ADDRESS.matcher(text).matches()){
               _webMailState.value = 0
            }
        }
    }

    //학교 웹메일 텍스트 삭제
    fun deleteMailInput() {
        _webMailInput.value = ""
    }

    //이메일 인증 상태
    fun changeWebMailState() {
        if(!TextUtils.isEmpty(_webMailInput.value) && Patterns.EMAIL_ADDRESS.matcher(_webMailInput.value).matches()) {
            _webMailState.value = 2
        }
        else _webMailState.value = 1
    }

    //코드 입력란
    fun codeInputText(cs : CharSequence) {
        cs.toString().let {
            if(_codeInput.value != it){
                _codeInput.value = it
            }
        }
    }

    //코드 인증 상탱
    fun changeCodeState(){
        val code = "1111"
        if(_codeInput.value == code){
            _codeState.value = 2
        }
        else _codeState.value = 1
    }

    //코드 텍스트 삭제
    fun deleteCodeInput() {
        _codeInput.value = ""
    }

    //사진 업로드하기
    fun changeUploadStatus(status : Boolean){
        _isUpload.value = status
    }

    //완료 버튼
    fun onSubmit(){
        if(_currentPage.value==0){
            //웹메일 인증에서 사용
        }
        else {
            //학생증 인증에서 사용
        }
    }

    //상태 초기화
    fun clearAllState(){
        _webMailInput.value = ""
        _codeInput.value = ""
        _webMailState.value = 0
        _codeState.value = 0
    }

    fun submitWithoutEmail(){
        //signModel.join()
    }

}