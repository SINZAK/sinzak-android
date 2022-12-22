package io.sinzak.android.ui.login

import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.R
import io.sinzak.android.databinding.ActivityRegisterBinding
import io.sinzak.android.enums.RegisterPage
import io.sinzak.android.enums.RegisterPage.*
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.login.agreement.AgreementFragment
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

        observeNavigation()
    }



    fun observeNavigation(){
        lifecycleScope.launch{
            viewModel.topPage.collect(::setPage)
        }
    }

    private var fragment : BaseFragment? = null

    fun setPage(page : RegisterPage)
    {
        fragment =
        when(page)
        {
            PAGE_AGREEMENT ->
                AgreementFragment()
            PAGE_UNIVERSITY_CERT ->
                CertificationFragment()
            PAGE_NAME ->
                TODO()
            PAGE_INTEREST ->
                TODO()
            PAGE_UNIVERSITY ->
                TODO()
        }

        supportFragmentManager.beginTransaction().replace(R.id.fc_register,fragment!!).commit()
    }
}