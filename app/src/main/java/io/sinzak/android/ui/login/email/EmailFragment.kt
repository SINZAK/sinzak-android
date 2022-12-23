package io.sinzak.android.ui.login.email

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentRegisterEmailBinding
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.login.RegisterActivity


@AndroidEntryPoint
class EmailFragment : BaseFragment() {

    lateinit var bind : FragmentRegisterEmailBinding

    val viewModel by activityViewModels<EmailViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentRegisterEmailBinding.inflate(layoutInflater)
        return bind.root
    }


    override fun navigateOnBackPressed() {

    }

    override fun showBottomBar(): Boolean {
        return false
    }
    override fun onFragmentCreated() {
        bind.apply{
            vm = viewModel
            fg = this@EmailFragment
            lifecycleOwner = viewLifecycleOwner
        }
    }


    fun dismiss(){
        (requireActivity() as RegisterActivity).gotoMain()

    }
}