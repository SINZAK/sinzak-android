package io.sinzak.android.ui.main.chat

import android.view.View
import io.sinzak.android.ui.base.BaseFragment

class ChatRoomFragment : BaseFragment() {
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