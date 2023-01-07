package io.sinzak.android.ui.login

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import io.sinzak.android.R
import io.sinzak.android.databinding.ActivityRegisterBinding
import io.sinzak.android.enums.RegisterPage
import io.sinzak.android.enums.RegisterPage.*
import io.sinzak.android.model.navigate.RegisterNavigation
import io.sinzak.android.system.LogDebug
import io.sinzak.android.system.LogInfo
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.login.agreement.AgreementFragment
import io.sinzak.android.ui.login.email.EmailFragment
import io.sinzak.android.ui.login.interest.InterestFragment
import io.sinzak.android.ui.login.name.NameFragment
import io.sinzak.android.ui.main.MainActivity
import io.sinzak.android.ui.main.profile.certification.CertificationFragment
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {



    private val viewModel by viewModels<RegisterViewModel>()
    override fun onActivityCreate() {

        useBind {
            vm = viewModel
            activity = this@RegisterActivity

        }

        LogDebug(javaClass.name,"[REGISTER ACTIVITY] ${viewModel.signModel.isLogin.value}")
        viewModel.regNav.changePage(PAGE_AGREEMENT)
        observeNavigation()
    }



    fun observeNavigation(){
        lifecycleScope.launch{
            viewModel.topPage.collect(::setPage)
        }

        lifecycleScope.launch{
            viewModel.signModel.isLogin.collect{
                if(it)
                    gotoMain()
            }
        }

    }

    private var fragment : BaseFragment? = null

    fun setPage(page : RegisterPage)
    {
        LogDebug(javaClass.name,"NEXT PAGE : $page")
        fragment =
        when(page)
        {
            PAGE_AGREEMENT ->
                AgreementFragment()
            PAGE_UNIVERSITY_CERT ->
                EmailFragment()
            PAGE_NAME ->
                NameFragment()
            PAGE_INTEREST ->
                InterestFragment()
            PAGE_UNIVERSITY ->
                CertificationFragment()
        }

        supportFragmentManager.beginTransaction().replace(R.id.fc_register,fragment!!).commit()

    }

    fun gotoMain(){

        LogInfo(javaClass.name,"메인으로 가자")
        finish()
        /*Intent(this,MainActivity::class.java).apply{
            addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY)
            startActivity(this)
        }*/
    }


}