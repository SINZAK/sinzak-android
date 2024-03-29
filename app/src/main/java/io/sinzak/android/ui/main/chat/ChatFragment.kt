package io.sinzak.android.ui.main.chat

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.databinding.FragmentChatBinding
import io.sinzak.android.ui.main.chat.viewmodel.ChatViewModel


@AndroidEntryPoint
open class ChatFragment : BaseFragment() {

    private val viewModel by activityViewModels<ChatViewModel>()
    lateinit var bind : FragmentChatBinding
    override fun getFragmentRoot(): View {
        bind = FragmentChatBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun navigateOnBackPressed() {
        viewModel.backPressedToExitApp {
            activity?.finish()
        }
    }

    override fun onFragmentCreated() {
        bind.apply{
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel

        }

        viewModel.getRemoteChatRoomList()
        viewModel.fetchRoomListJob()
    }

    override fun showBottomBar(): Boolean {
        return true
    }

    override fun onPause() {
        super.onPause()
        viewModel.clearJob()
    }

    override fun onStop() {
        super.onStop()
        viewModel.clearJob()
    }
}