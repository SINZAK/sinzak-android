package io.sinzak.android.ui.login

import android.content.Intent
import androidx.activity.viewModels
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.R
import io.sinzak.android.databinding.ActivityLoginBinding
import io.sinzak.android.system.LogDebug
import io.sinzak.android.system.social.NaverImpl
import io.sinzak.android.ui.base.BaseActivity
import javax.inject.Inject


@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {


    private val viewModel by viewModels<LoginViewModel>()
    @Inject
    lateinit var connect: LoginConnect

    override fun onActivityCreate() {
        useBind {
            activity = this@LoginActivity
            vm = viewModel
        }

        viewModel.signModel.initSignStatus()
        viewModel.signModel.registerLoginActivity(this)

        observation()
    }

    override fun onResume() {
        super.onResume()
        viewModel.registerConnect(connect, this as BaseActivity<*>)
    }

    private fun observation(){
        invokeBooleanFlow(viewModel.signModel.sdkSignSuccess){
            showToast("소셜 로그인에 성공했어요")
        }
        invokeBooleanFlow(viewModel.signModel.isLogin){
            finish()
        }
    }

    fun onSuccessLogin(status : Boolean ) : Boolean{
        LogDebug(javaClass.name,"로그인 석세스 $status")
        if(status)
        {
            startActivity(Intent(this,RegisterActivity::class.java).apply{

            })
            finish()
        }



        return false
    }



    fun requestKakaoLoginActivity(userClient : UserApiClient,  callback : (OAuthToken?, Throwable?)->Unit, forceWeb: Boolean = false)
    {
        if(!userClient.isKakaoTalkLoginAvailable(this) || forceWeb)
            userClient.loginWithKakaoAccount(this, callback = callback)
        else
            userClient.loginWithKakaoTalk(this, callback = callback)
    }

    fun requestNaverLoginActivity(naverCallback : NaverImpl)
    {
        NaverIdLoginSDK.authenticate(this,naverCallback)
    }

}