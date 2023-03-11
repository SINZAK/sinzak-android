package io.sinzak.android.ui.main.chat

import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentChatroomBinding
import io.sinzak.android.system.LogDebug
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.chat.viewmodel.ChatroomFormViewModel
import io.sinzak.android.ui.main.chat.viewmodel.ChatroomViewModel

@AndroidEntryPoint
class ChatRoomFragment : BaseFragment() {

    private val viewModel: ChatroomViewModel by viewModels()
    private val chatSendViewModel: ChatroomFormViewModel by viewModels()

    private lateinit var bind: FragmentChatroomBinding
    override fun getFragmentRoot(): View {
        bind = FragmentChatroomBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply{
            formVm = chatSendViewModel
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        LogDebug(javaClass.name, "내 아이디 : ${viewModel.myId}")


    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun navigateOnBackPressed() {
        viewModel.onBackPressed()
    }


}