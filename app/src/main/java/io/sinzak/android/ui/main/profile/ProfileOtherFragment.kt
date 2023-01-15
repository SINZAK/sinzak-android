package io.sinzak.android.ui.main.profile

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileOtherFragment : ProfileFragment() {

    override fun onFragmentCreated() {
        profileModel.getUserProfile(viewModel.userId.value)
        inflateOtherChild()
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }

    override fun showBottomBar(): Boolean {
        return false
    }
}