package io.sinzak.android.ui.login.agreement

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentRegisterAgreementBinding
import io.sinzak.android.ui.base.BaseFragment

@AndroidEntryPoint
class AgreementFragment : BaseFragment() {

    lateinit var bind : FragmentRegisterAgreementBinding



    val viewModel by activityViewModels<AgreementViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentRegisterAgreementBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun navigateOnBackPressed() {

    }

    override fun onFragmentCreated() {
        bind.apply{
            lifecycleOwner = viewLifecycleOwner
            fg = this@AgreementFragment
            vm = viewModel
        }
    }

    override fun showBottomBar(): Boolean {
        return false
    }
}