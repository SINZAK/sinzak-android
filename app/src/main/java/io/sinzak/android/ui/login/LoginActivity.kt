package io.sinzak.android.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import io.sinzak.android.ui.main.MainActivity


@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {


    override fun onActivityCreate() {
        useBind {
            activity = this@LoginActivity
            vm = viewModel
        }

        viewModel.signModel.initSignStatus()
        viewModel.signModel.registerLoginActivity(this)

        observation()
    }



    fun observation(){
        invokeBooleanFlow(viewModel.signModel.sdkSignSuccess){
            showToast("소셜 로그인에 성공했습니다.")
        }
    }

    val viewModel by viewModels<LoginViewModel>()


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



    fun requestKakaoLoginActivity(userClient : UserApiClient,  callback : (OAuthToken?, Throwable?)->Unit)
    {
        if(!userClient.isKakaoTalkLoginAvailable(this))
            userClient.loginWithKakaoAccount(this, callback = callback)
        else
            userClient.loginWithKakaoTalk(this, callback = callback)

    }

    fun requestNaverLoginActivity(naverCallback : NaverImpl)
    {
        NaverIdLoginSDK.authenticate(this,naverCallback)
    }
}