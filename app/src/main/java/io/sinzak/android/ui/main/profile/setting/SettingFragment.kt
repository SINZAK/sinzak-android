package io.sinzak.android.ui.main.profile.setting

import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentSettingProfileBinding
import io.sinzak.android.ui.base.BaseFragment

@AndroidEntryPoint
class SettingFragment : BaseFragment() {

    private lateinit var bind : FragmentSettingProfileBinding

    override fun getFragmentRoot(): View {
        bind = FragmentSettingProfileBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply {
            bind.lifecycleOwner = viewLifecycleOwner
            fg = this@SettingFragment
        }
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }
}