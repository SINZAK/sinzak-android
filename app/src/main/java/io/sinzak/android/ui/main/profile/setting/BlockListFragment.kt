package io.sinzak.android.ui.main.profile.setting

import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentSettingBlocklistBinding
import io.sinzak.android.ui.base.BaseFragment

@AndroidEntryPoint
class BlockListFragment : BaseFragment() {

    private lateinit var bind : FragmentSettingBlocklistBinding

    private val viewModel by viewModels<BlockListViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentSettingBlocklistBinding.inflate(layoutInflater)
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