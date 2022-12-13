package io.sinzak.android.ui.main.profile.certification

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentVerifyBinding
import io.sinzak.android.enums.Page
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.profile.edit.EditViewModel

@AndroidEntryPoint
class VerifyFragment : BaseFragment(){

    private lateinit var bind: FragmentVerifyBinding

    private val editViewModel : EditViewModel by activityViewModels<EditViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentVerifyBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply {
            bind.lifecycleOwner = viewLifecycleOwner
            eVm = editViewModel
            fg = this@VerifyFragment
        }
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }

    //겹치는 메서드
    fun gotoCertificationPage(hasCerti : Boolean){
        if(!hasCerti) navigator.changePage(Page.PROFILE_CERTIFICATION)
        else return
    }
}