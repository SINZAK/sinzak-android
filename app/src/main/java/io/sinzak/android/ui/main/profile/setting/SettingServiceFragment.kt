package io.sinzak.android.ui.main.profile.setting

import android.view.View
import androidx.fragment.app.activityViewModels
import io.sinzak.android.databinding.FragmentSettingServiceBinding
import io.sinzak.android.ui.base.BaseFragment

class SettingServiceFragment : BaseFragment(){

    private lateinit var bind : FragmentSettingServiceBinding

    private val viewModel by activityViewModels<SettingViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentSettingServiceBinding.inflate(layoutInflater)
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