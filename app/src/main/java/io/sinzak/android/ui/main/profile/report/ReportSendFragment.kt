package io.sinzak.android.ui.main.profile.report

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentReportSendBinding
import io.sinzak.android.ui.base.BaseFragment

@AndroidEntryPoint
class ReportSendFragment : BaseFragment() {

    private lateinit var bind : FragmentReportSendBinding

    private val viewModel : ReportSendViewModel by activityViewModels()

    override fun getFragmentRoot(): View {
        bind = FragmentReportSendBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply {
            bind.lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            fg = this@ReportSendFragment
        }
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }
}