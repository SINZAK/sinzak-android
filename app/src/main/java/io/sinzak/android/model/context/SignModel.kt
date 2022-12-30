package io.sinzak.android.model.context

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthBehavior
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.sinzak.android.constants.*
import io.sinzak.android.enums.SDK
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.request.login.LoginEmailBody
import io.sinzak.android.remote.dataclass.request.login.TokenRequest
import io.sinzak.android.remote.dataclass.response.login.NaverProfile
import io.sinzak.android.remote.dataclass.response.login.Token
import io.sinzak.android.remote.retrofit.CallImpl
import io.sinzak.android.remote.retrofit.Remote
import io.sinzak.android.remote.retrofit.RemoteListener
import io.sinzak.android.system.App.Companion.prefs
import io.sinzak.android.system.LogError
import io.sinzak.android.system.LogInfo
import io.sinzak.android.system.social.NaverImpl
import io.sinzak.android.ui.login.LoginActivity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SignModel @Inject constructor(val remote : Remote) : RemoteListener {

    private val _isLogin = MutableStateFlow(false)
    val isLogin : StateFlow<Boolean> get() = _isLogin

    fun isLogin() : Boolean{
        return  isLogin.value
    }

    fun checkToken(){

        CallImpl(
            API_REFRESH_TOKEN,
            this,
            TokenRequest(
                accessToken = prefs.accessToken,
                refreshToken = prefs.refreshToken
            )
        ).apply{
            remote.sendRequestApi(this)
        }

    }


    private val _sdkSignSuccess = MutableStateFlow(false)
    val sdkSignSuccess : StateFlow<Boolean> get() = _sdkSignSuccess

    private val _signFailed = MutableStateFlow(false)
    private val _errorString = MutableStateFlow("")

    var sdkType : SDK? = null

    private var loginEmail : String = ""


    lateinit var loginIntentActivity : LoginActivity

    fun registerLoginActivity(activity: LoginActivity)
    {
        loginIntentActivity = activity
    }

    private lateinit var naverCallback : NaverImpl

    private lateinit var loginKaKao : UserApiClient


    fun initSignStatus(){
        _signFailed.value = false
        _errorString.value = ""
        _sdkSignSuccess.value = false
    }


    private fun initNaverSdk(){
        if(!::naverCallback.isInitialized)
            naverCallback = NaverImpl(_sdkSignSuccess,_signFailed,_errorString)
    }

    private fun initKakaoSdk(){
        if(!::loginKaKao .isInitialized)
            loginKaKao = UserApiClient()
    }


    fun loginViaNaver(){

        initSignStatus()
        sdkType = SDK.NAVER
        initNaverSdk()
        //todo : Login With Naver

        NaverIdLoginSDK.behavior = NidOAuthBehavior.DEFAULT
        loginIntentActivity.requestNaverLoginActivity(naverCallback)

    }

    fun onSuccessNaverLogin()
    {
        NaverIdLoginSDK.getAccessToken()?.let{token->
            CallImpl(API_EMAIL_GET_NAVER,
            this,
            paramStr0 = token).apply{
                remote.sendRequestApi(this)
            }
        }
    }


    fun loginViaKakao(){
        initSignStatus()
        initKakaoSdk()

        loginIntentActivity.requestKakaoLoginActivity(loginKaKao,::onLoginKakao)



        //todo : Login With Kakao
    }

    private fun onLoginKakao(token : OAuthToken?, error : Throwable?)
    {
        error?.let{
            _errorString.value = it.toString()
            LogError(error)
            _signFailed.value = true
            return
        }
        token?.let{
            _sdkSignSuccess.value = true
            LogInfo(javaClass.name,"카카오 로그인 성공 : $token")
            getKakaoEmail()

        }?:run{
            _signFailed.value = true
            _errorString.value = "토큰 존재하지 않음"
            LogError(javaClass.name,"토큰이 존재하지 않습니다.")
        }
    }

    fun getKakaoEmail(){
        loginKaKao.me { user, error ->

            error?.let{
                LogError(error)
            }

            user?.let{
                it.kakaoAccount?.email?.let{
                    _sdkSignSuccess.value = true
                    loginEmail = it
                    sdkType = SDK.KAKAO
                    loginToServer()
                   return@me
                }

                _signFailed.value = true

            }


        }

    }

    fun loginToServer(){
        CallImpl(
            API_LOGIN_EMAIL,
            this,
            LoginEmailBody(
                email = loginEmail
            )
        ).apply{
            remote.sendRequestApi(this)
        }
    }

    fun loginViaGoogle(){

        //todo : Login With Google

    }

    fun onRefreshToken(response : Token)
    {
        if(response.accessToken.isNullOrEmpty())
            return

        _isLogin.value = true
        prefs.setString(ACCESS_TOKEN,response.accessToken)
        prefs.setString(REFRESH_TOKEN,response.refreshToken)
    }


    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api)
        {
            API_LOGIN_EMAIL ->{

            }

            API_REFRESH_TOKEN ->{
                onRefreshToken(body as Token)
            }

            API_EMAIL_GET_NAVER ->{
                if(body is NaverProfile)
                {
                    loginEmail = body.profile?.email.toString()

                    loginToServer()
                }

            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {
        _errorString.value = msg.toString()
        _signFailed.value = true

        when (api)
        {
            API_LOGIN_EMAIL ->{
                _isLogin.value = true
            }
        }
    }


}