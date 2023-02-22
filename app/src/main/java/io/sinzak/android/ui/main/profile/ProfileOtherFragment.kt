package io.sinzak.android.ui.main.profile

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileOtherFragment : ProfileFragment() {

    override fun onFragmentCreated() {
        viewModel.getOtherProfileRemote()
        inflateOtherChild()
    }


    override fun navigateOnBackPressed() {
        viewModel.onBackPressed()
    }

    override fun showBottomBar(): Boolean {
        return false
    }
}