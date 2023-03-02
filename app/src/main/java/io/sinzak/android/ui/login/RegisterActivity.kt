package io.sinzak.android.ui.login

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.R
import io.sinzak.android.databinding.ActivityRegisterBinding
import io.sinzak.android.enums.RegisterPage
import io.sinzak.android.enums.RegisterPage.*
import io.sinzak.android.system.LogDebug
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.login.agreement.AgreementFragment
import io.sinzak.android.ui.login.cert.RegisterCertFragment
import io.sinzak.android.ui.login.email.EmailFragment
import io.sinzak.android.ui.login.interest.InterestFragment
import io.sinzak.android.ui.login.name.NameFragment
import io.sinzak.android.ui.login.welcome.WelcomeFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>(R.layout.activity_register) {


    @Inject
    lateinit var registerConnect: RegisterConnect

    private val viewModel by viewModels<RegisterViewModel>()
    override fun onActivityCreate() {

        useBind {
            vm = viewModel
            activity = this@RegisterActivity

        }

        LogDebug(javaClass.name,"[REGISTER ACTIVITY] ${viewModel.signModel.isLogin.value}")
        viewModel.regNav.changePage(PAGE_AGREEMENT)
        observeNavigation()

        registerConnect.registerActivityContext(this, this as BaseActivity<*>)

    }



    fun observeNavigation(){
        lifecycleScope.launch{
            viewModel.topPage.collect(::setPage)
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
                RegisterCertFragment()
            Welcome ->
                WelcomeFragment()
        }

        supportFragmentManager.beginTransaction().replace(R.id.fc_register,fragment!!).commit()

    }

    override fun onBackPressed() {
        if (viewModel.regNav.revealHistory()) return
        else super.onBackPressed()
    }
}