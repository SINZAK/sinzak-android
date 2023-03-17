package io.sinzak.android.ui.login.name

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.login.RegisterConnect
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor(
    val connect: RegisterConnect
    ) :
    BaseViewModel() {


    var typedName = ""
    var nameCheckMsg = ""

    val nameCheckStatus = MutableStateFlow(INIT)

    fun typeName(text: CharSequence) {
        typedName = text.toString()
    }

    private fun isNameValidate(): Boolean {

        if(typedName.length < 2){
            nameCheckMsg = "닉네임은 두 글자 이상 설정해주세요"
            return false
        }

        if (typedName.contains(' ')){
            nameCheckMsg = "닉네임은 공백없이 설정해주세요"
            return false
        }

        typedName.forEach {
            if (it.code !in 0xAC00..0xD7AF && it.code !in 0x41..0x5A && it.code !in 0x61..0x7A && it !in "._-" && it !in "0123456789"){
                nameCheckMsg = "닉네임 기호는 - _ . 만 사용 가능합니다"
                return false
            }
        }

        return true

    }

    fun onNameCheck(){
        if (!isNameValidate()) {
            uiModel.showToast(nameCheckMsg)
            return
        }

        signModel.checkName(typedName)

        invokeBooleanFlow(
            signModel.nameCheckSuccessFlag,
            {
                nameCheckStatus.value = ERROR
            },
            {
                nameCheckStatus.value = CHECK
            }
        )

    }

    fun onSubmit() {
        connect.gotoCategoryPage()
    }

    fun onBackPressed(){
        connect.navigation.revealHistory()
    }

    companion object {
        const val INIT = 0
        const val CHECK = 1
        const val ERROR = 2
    }
}