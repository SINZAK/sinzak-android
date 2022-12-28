package io.sinzak.android.ui.login

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.context.SignModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.sign

@HiltViewModel
class LoginViewModel @Inject constructor(val signModel: SignModel) : BaseViewModel() {


    val loginSuccess get() =  signModel.signInSuccess


    fun loginKakao()
     = signModel.loginViaKakao()

    fun loginNaver() {
        signModel.loginViaNaver()
        invokeBooleanFlow(signModel.sdkSignSuccess){

        }
    }

    fun loginGoogle()
     = signModel.loginViaGoogle()




}