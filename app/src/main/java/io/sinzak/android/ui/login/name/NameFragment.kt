package io.sinzak.android.ui.login.name

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentRegisterNameBinding
import io.sinzak.android.ui.base.BaseFragment

@AndroidEntryPoint
class NameFragment() : BaseFragment() {

    lateinit var bind : FragmentRegisterNameBinding

    private val viewModel by activityViewModels<NameViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentRegisterNameBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun navigateOnBackPressed() {
        viewModel.onBackPressed()
    }


    override fun showBottomBar(): Boolean {
        return false
    }


    override fun onFragmentCreated() {
        bind.apply{
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
    }
}