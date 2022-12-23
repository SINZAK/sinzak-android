package io.sinzak.android.ui.main.profile.report

import android.view.View
import io.sinzak.android.databinding.FragmentReportTypeBinding
import io.sinzak.android.ui.base.BaseFragment

class ReportTypeFragment : BaseFragment() {

    private lateinit var bind : FragmentReportTypeBinding

    override fun getFragmentRoot(): View {
        bind = FragmentReportTypeBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply {
            bind.lifecycleOwner = lifecycleOwner
            fg = this@ReportTypeFragment
        }
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }

    //신고 전송 페이지로
    fun goToReportSendPage(){

    }
}