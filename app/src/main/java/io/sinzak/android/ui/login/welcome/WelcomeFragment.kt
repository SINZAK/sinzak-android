package io.sinzak.android.ui.login.welcome

import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.databinding.FragmentRegisterWelcomeBinding

@AndroidEntryPoint
class WelcomeFragment : BaseFragment() {
    private lateinit var bind : FragmentRegisterWelcomeBinding

    val viewModel by viewModels<WelcomeViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentRegisterWelcomeBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun navigateOnBackPressed() {

    }

    override fun onFragmentCreated() {
        bind.vm = viewModel
    }

    override fun showBottomBar(): Boolean {
        return false
    }

}