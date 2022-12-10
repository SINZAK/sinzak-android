package io.sinzak.android.ui.login

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.context.SignModel
import io.sinzak.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val signModel: SignModel) : BaseViewModel() {


    val loginSuccess get() =  signModel.signInSuccess


    fun loginKakao()
     = signModel.loginViaKakao()

    fun loginNaver()
     = signModel.loginViaNaver()

    fun loginGoogle()
     = signModel.loginViaGoogle()
}