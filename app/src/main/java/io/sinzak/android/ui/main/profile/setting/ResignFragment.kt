package io.sinzak.android.ui.main.profile.setting

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentResignBinding
import io.sinzak.android.ui.base.BaseFragment

@AndroidEntryPoint
class ResignFragment : BaseFragment() {

    private lateinit var bind : FragmentResignBinding

    private val viewModel by activityViewModels<SettingViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentResignBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply {
            lifecycleOwner = viewLifecycleOwner
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