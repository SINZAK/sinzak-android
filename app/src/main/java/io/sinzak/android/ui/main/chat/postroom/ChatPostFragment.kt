package io.sinzak.android.ui.main.chat.postroom

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentChatPostBinding
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.chat.viewmodel.ChatViewModel

@AndroidEntryPoint
class ChatPostFragment : BaseFragment() {

    private lateinit var bind : FragmentChatPostBinding
    private val viewModel by activityViewModels<ChatViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentChatPostBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
        }
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun navigateOnBackPressed() {
        viewModel.onBackPressed()
    }
}