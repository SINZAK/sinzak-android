package io.sinzak.android.ui.main.profile.edit

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentEditProfileBinding
import io.sinzak.android.enums.Page
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.profile.viewmodel.ProfileViewModel

@AndroidEntryPoint
class EditFragment : BaseFragment() {

    private lateinit var bind : FragmentEditProfileBinding

    private val viewModel : EditViewModel by activityViewModels()
    private val profileViewModel by activityViewModels<ProfileViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentEditProfileBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply {
            bind.lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            pVm = profileViewModel
            fg = this@EditFragment
        }
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }

    fun gotoCertificationPage(hasCerti : Boolean){
        if(!hasCerti) navigator.changePage(Page.PROFILE_CERTIFICATION)
        else return
    }

    fun gotoVerifyPage(){
        navigator.changePage(Page.PROFILE_VERIFY)
    }
}