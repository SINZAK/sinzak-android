package io.sinzak.android.model.context

import android.content.Context
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.sinzak.android.enums.SDK
import io.sinzak.android.system.LogDebug
import io.sinzak.android.system.social.NaverImpl
import io.sinzak.android.ui.login.LoginActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton



class SignModel @Inject constructor(val context : Context) {


    private val _signInSuccess = MutableStateFlow(false)
    val signInSuccess : StateFlow<Boolean> get() = _signInSuccess
    private val _sdkSignSuccess = MutableStateFlow(false)
    val sdkSignSuccess : StateFlow<Boolean> get() = _sdkSignSuccess

    private val _signFailed = MutableStateFlow(false)
    private val _errorString = MutableStateFlow("")

    var sdkType : SDK? = null


    lateinit var loginIntentActivity : LoginActivity

    fun registerLoginActivity(activity: LoginActivity)
    {
        loginIntentActivity = activity
    }

    private val naverCallback = NaverImpl(_sdkSignSuccess,_signFailed,_errorString)



    fun loginViaNaver(){


        //todo : Login With Naver

        NaverIdLoginSDK.initialize(context,"CLIENT_ID","CLIENT_SECRET","CLIENT_NAME")
        NaverIdLoginSDK.authenticate(context,naverCallback)

        _signInSuccess.value = true
    }

    fun onSuccessNaverLogin()
    {
        NaverIdLoginSDK.getAccessToken()
    }


    fun loginViaKakao(){

        //todo : Login With Kakao
    }

    fun loginViaGoogle(){

        //todo : Login With Google

    }



    @dagger.Module
    @InstallIn(SingletonComponent::class)
    internal object Module{
        @Provides
        fun provideModel(@ApplicationContext context : Context) : SignModel
        {
            return SignModel(context)
        }
    }


}