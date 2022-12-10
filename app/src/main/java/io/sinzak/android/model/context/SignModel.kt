package io.sinzak.android.model.context

import io.sinzak.android.system.LogDebug
import io.sinzak.android.ui.login.LoginActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SignModel @Inject constructor() {


    private val _signInSuccess = MutableStateFlow(false)
    val signInSuccess : StateFlow<Boolean> get() = _signInSuccess


    lateinit var loginIntentActivity : LoginActivity

    fun registerLoginActivity(activity: LoginActivity)
    {
        loginIntentActivity = activity
    }



    fun loginViaNaver(){


        //todo : Login With Naver


        _signInSuccess.value = true
    }


    fun loginViaKakao(){

        //todo : Login With Kakao
    }

    fun loginViaGoogle(){

        //todo : Login With Google

    }





}