package io.sinzak.android.ui.login

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.SDK
import io.sinzak.android.model.context.SignModel
import io.sinzak.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {


    val loginSuccess get() =  signModel.isLogin
    val needSignUp get() = signModel.needSignUp


    fun loginKakao() {
        signModel.loginViaKakao()
        invokeBooleanFlow(signModel.sdkSignSuccess)
        {

        }
    }

    fun loginNaver() {
        signModel.loginViaNaver()
        invokeBooleanFlow(signModel.sdkSignSuccess){
            if(signModel.sdkType == SDK.naver)
                signModel.onSuccessNaverLogin()
        }
    }

    fun loginGoogle() {
        signModel.loginViaGoogle()
        invokeStateFlow(signModel.sdkSignSuccess)
        {

        }
    }

    /**
     * 임시함수
     */
    fun loginViaEmailTemp()
    {
        signModel.loginEmailTemp("rio319@naver.com")
    }




}