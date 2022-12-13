package io.sinzak.android.ui.main.profile.certification

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentWebmailBinding
import io.sinzak.android.ui.base.BaseFragment

@AndroidEntryPoint
class WebmailFragment : BaseFragment() {

    private lateinit var bind: FragmentWebmailBinding

    private val viewModel : WebmailViewModel by activityViewModels()
    private val certificationViewModel : CertificationViewModel by activityViewModels<CertificationViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentWebmailBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply {
            bind.lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            cVm = certificationViewModel
            fg = this@WebmailFragment
        }
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }
}