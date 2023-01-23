package io.sinzak.android.ui.main.profile

import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.system.LogDebug

@AndroidEntryPoint
class ProfileOtherFragment : ProfileFragment() {

    override fun onFragmentCreated() {
        viewModel.getOtherProfileRemote()
        inflateOtherChild()
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
        viewModel.revealProfileHistory()
    }

    override fun showBottomBar(): Boolean {
        return false
    }
}