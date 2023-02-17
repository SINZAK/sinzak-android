package io.sinzak.android.ui.main.profile.certification

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentVerifyBinding
import io.sinzak.android.enums.Page
import io.sinzak.android.ui.base.BaseFragment

@AndroidEntryPoint
class VerifyFragment : BaseFragment(){

    private lateinit var bind: FragmentVerifyBinding

    private val viewModel : VerifyViewModel by activityViewModels()

    override fun getFragmentRoot(): View {
        bind = FragmentVerifyBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply {
            bind.lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun navigateOnBackPressed() {
        viewModel.onBackPressed()
    }

}