package io.sinzak.android.ui.main.home.notification

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentNotificationBinding
import io.sinzak.android.ui.base.BaseFragment


@AndroidEntryPoint
class NotificationFragment : BaseFragment() {

    private lateinit var bind : FragmentNotificationBinding

    private val viewModel : NotificationViewModel by activityViewModels()

    override fun getFragmentRoot(): View {
        bind = FragmentNotificationBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun navigateOnBackPressed() {
        viewModel.onBackPressed()
    }

    override fun onFragmentCreated() {
        bind.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }

        viewModel.getNotificationList()
    }

    override fun showBottomBar(): Boolean {
        return false
    }
}