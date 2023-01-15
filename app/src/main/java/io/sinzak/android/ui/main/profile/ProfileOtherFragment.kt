package io.sinzak.android.ui.main.profile

import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.system.LogDebug

@AndroidEntryPoint
class ProfileOtherFragment : ProfileFragment() {

    override fun onFragmentCreated() {
        LogDebug(javaClass.name,"타인 아이디: ${viewModel.userId}")
        profileModel.getUserProfile()
        inflateOtherChild()
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }

    override fun showBottomBar(): Boolean {
        return false
    }
}