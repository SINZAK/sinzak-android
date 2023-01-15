package io.sinzak.android.ui.main.profile.sale_with_work

import android.view.View
import io.sinzak.android.ui.base.BaseFragment

class RequestFragment : BaseFragment() {
    override fun getFragmentRoot(): View {
        TODO("Not yet implemented")
    }

    override fun onFragmentCreated() {
        TODO("Not yet implemented")
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }
}