package io.sinzak.android.ui.login

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.SDK
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : BaseViewModel() {

    private var _connect : LoginConnect? = null
    private val connect : LoginConnect get() = requireNotNull(_connect)

    val loginSuccess get() =  signModel.isLogin
    val needSignUp get() = signModel.needSignUp

    fun registerConnect(loginConnect: LoginConnect, a : BaseActivity<*>)
    {
        _connect = loginConnect
        connect.registerActivityContext(a)
    }


    fun loginKakao() {
        signModel.loginViaKakao()
        invokeBooleanFlow(signModel.sdkSignSuccess)
        {

        }
    }

    fun loginNaver() {
        signModel.loginViaNaver()
        invokeBooleanFlow(signModel.sdkSignSuccess){
            if(signModel.sdkType == SDK.Naver)
                signModel.onSuccessNaverLogin()
        }
    }

    fun loginGoogle() {
        connect.getGoogleAccount {
            signModel.loginViaGoogle(it)
        }
        invokeStateFlow(signModel.sdkSignSuccess)
        {

        }
    }

    /**
     * 임시함수
     */
    fun loginViaEmailTemp()
    {
        signModel.loginEmailTemp("univcertofficial@gmail.com")
    }




}