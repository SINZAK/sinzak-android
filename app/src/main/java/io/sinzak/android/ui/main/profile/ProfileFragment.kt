package io.sinzak.android.ui.main.profile

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.R
import io.sinzak.android.databinding.FragmentProfileBinding
import io.sinzak.android.databinding.ViewProfileArtSaleBinding
import io.sinzak.android.databinding.ViewProfileArtWorkBinding
import io.sinzak.android.databinding.ViewProfileLinkListBinding
import io.sinzak.android.databinding.ViewProfileMyprofileBinding
import io.sinzak.android.databinding.ViewProfileTopAppbarBinding
import io.sinzak.android.enums.Page
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.market.artdetail.ArtistBlockDialog
import io.sinzak.android.ui.main.market.artdetail.ArtistReportDialog
import io.sinzak.android.ui.main.profile.edit.EditViewModel
import io.sinzak.android.ui.main.profile.report.ReportSendViewModel
import io.sinzak.android.ui.main.profile.viewmodel.ProfileSaleViewModel
import io.sinzak.android.ui.main.profile.viewmodel.ProfileViewModel
import io.sinzak.android.ui.main.profile.viewmodel.ProfileWorkViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment :BaseFragment() {

    private lateinit var bind : FragmentProfileBinding

    private val viewModel : ProfileViewModel by activityViewModels()
    private val profileSaleViewModel by activityViewModels<ProfileSaleViewModel>()
    private val profileWorkViewModel by activityViewModels<ProfileWorkViewModel>()
    private val editProfileViewModel by activityViewModels<EditViewModel>()
    private val reportSendViewModel by activityViewModels<ReportSendViewModel>()


    override fun getFragmentRoot(): View {
        bind = FragmentProfileBinding.inflate(layoutInflater)
        bind.vm = viewModel
        bind.lifecycleOwner = viewLifecycleOwner
        return bind.root
    }

    override fun onFragmentCreated() {
        inflateChild()
    }

    override fun showBottomBar(): Boolean {
        return true
    }

    override fun navigateOnBackPressed() {

    }

    /**************v
     *
     * VIEW INFLATING
     *
     ***************/
    private fun inflateChild() {
        inflateAppbar()
        inflateMyProfile()
        inflateLinkList()
        inflateArtSale()
        inflateArtWork()
    }

    private fun inflateAppbar(){
        ViewProfileTopAppbarBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            fg = this@ProfileFragment
            bind.flAppbar.addView(root)
        }
    }
    private fun inflateMyProfile(){
        ViewProfileMyprofileBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            pVm = viewModel
            eVm = editProfileViewModel
            fg = this@ProfileFragment
            bind.llProfiles.addView(root)
        }
    }
    private fun inflateLinkList(){
        ViewProfileLinkListBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            bind.llProfiles.addView(root)
        }
    }
    private fun inflateArtSale(){
        ViewProfileArtSaleBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = profileSaleViewModel
            fg = this@ProfileFragment
            bind.llProfiles.addView(root)
        }
    }
    private fun inflateArtWork() {
        ViewProfileArtWorkBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = profileWorkViewModel
            fg = this@ProfileFragment
            bind.llProfiles.addView(root)
        }
    }


    //?????? ????????? ??????
    fun gotoSettingPage() {
        navigator.changePage(Page.PROFILE_SETTING)
    }

    // ??? ?????? ???????????????
    fun showMoreMenu(){
        ArtistReportDialog(requireContext(),
           viewModel.profile.value!!.name,{ goToReportPage()}, { showBlockDialog() }).show()
    }

    // ???????????? ???????????????
    fun showBlockDialog(){
        ArtistBlockDialog(requireContext(),{/*?????? api*/}).show()
    }
    // ????????? ?????? ????????????
    fun goToReportPage(){
        reportSendViewModel.isFromProfile(true)
        navigator.changePage(Page.PROFILE_REPORT_TYPE)
    }

    //????????? ?????? ??????
    fun gotoProfileEditPage() {
        navigator.changePage(Page.PROFILE_EDIT)
    }

    // ????????? ??????
    fun follow() {

    }

    //???????????? ??????
    fun goToChat() {

    }

    //????????? ?????? ??????
    fun gotoScrapPage(){

    }

    //???????????? ??????
    fun gotoRequestPage(){

    }

    //?????? ?????? ??????
    fun gotoSalePage(){
        navigator.changePage(Page.PROFILE_SALE)
    }

    // ???????????? ?????????
    fun gotoWorkPage(){
        navigator.changePage(Page.PROFILE_WORK)
    }
}