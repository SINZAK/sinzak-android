package io.sinzak.android.ui.login

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.context.SignModel
import io.sinzak.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val signModel: SignModel) : BaseViewModel() {


    val loginSuccess get() =  signModel.isLogin


    fun loginKakao() {
        signModel.loginViaKakao()
        invokeBooleanFlow(signModel.sdkSignSuccess)
        {

        }
    }

    fun loginNaver() {
        signModel.loginViaNaver()
        invokeBooleanFlow(signModel.sdkSignSuccess){

        }
    }

    fun loginGoogle()
     = signModel.loginViaGoogle()




}