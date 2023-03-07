package io.sinzak.android.ui.main.chat

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.databinding.FragmentChatBinding
import io.sinzak.android.ui.main.chat.viewmodel.ChatViewModel


@AndroidEntryPoint
class ChatFragment : BaseFragment() {

    private val viewModel by activityViewModels<ChatViewModel>()
    lateinit var bind : FragmentChatBinding
    override fun getFragmentRoot(): View {
        bind = FragmentChatBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun navigateOnBackPressed() {

    }

    override fun onFragmentCreated() {
        bind.apply{
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel

        }

        viewModel.getRemoteChatRoomList()
    }

    override fun showBottomBar(): Boolean {
        return true
    }


}