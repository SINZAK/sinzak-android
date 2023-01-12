package io.sinzak.android.ui.main.profile

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
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
import io.sinzak.android.ui.main.market.artdetail.ContentViewModel
import io.sinzak.android.ui.main.profile.edit.EditViewModel
import io.sinzak.android.ui.main.profile.report.ReportSendViewModel
import io.sinzak.android.ui.main.profile.viewmodel.ProfileSaleViewModel
import io.sinzak.android.ui.main.profile.viewmodel.ProfileViewModel
import io.sinzak.android.ui.main.profile.viewmodel.ProfileWorkViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment :BaseFragment() {

    private lateinit var bind : FragmentProfileBinding

    private val viewModel by activityViewModels<ProfileViewModel>()
    private val profileSaleViewModel by activityViewModels<ProfileSaleViewModel>()
    private val profileWorkViewModel by activityViewModels<ProfileWorkViewModel>()
    private val editProfileViewModel by activityViewModels<EditViewModel>()
    private val reportSendViewModel by activityViewModels<ReportSendViewModel>()

    private val contentViewModel by activityViewModels<ContentViewModel>()

    @Inject
    lateinit var profileModel: ProfileModel


    override fun getFragmentRoot(): View {
        bind = FragmentProfileBinding.inflate(layoutInflater)
        bind.vm = viewModel
        bind.lifecycleOwner = viewLifecycleOwner
        return bind.root
    }

    override fun onFragmentCreated() {

        if(viewModel.isMyProfile.value) {
            profileModel.getMyProfile()
        }
        else {
//            viewModel.profileModel.getUserProfile(contentViewModel.art.value.authorId)
            profileModel.getUserProfile("154")
        }
        inflateChild()
    }

    override fun showBottomBar(): Boolean {
        return true
    }

    override fun navigateOnBackPressed() {
        if (!viewModel.isMyProfile.value) navigator.revealHistory()
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


    //세팅 아이콘 클릭
    fun gotoSettingPage() {
        navigator.changePage(Page.PROFILE_SETTING)
    }

    // 더 보기 다이얼로그
    fun showMoreMenu(){
        ArtistReportDialog(requireContext(),
           viewModel.profile.value!!.name,{ goToReportPage()}, { showBlockDialog() }).show()
    }

    // 차단하기 다이얼로그
    fun showBlockDialog(){
        ArtistBlockDialog(requireContext(),{/*차단 api*/}).show()
    }
    // 사용자 신고 페이지로
    fun goToReportPage(){
        reportSendViewModel.isFromProfile(true)
        navigator.changePage(Page.PROFILE_REPORT_TYPE)
    }

    //프로필 편집 클릭
    fun gotoProfileEditPage() {
        navigator.changePage(Page.PROFILE_EDIT)
    }

    // 팔로우 버튼
    fun follow() {

    }

    //채팅하기 클릭
    fun goToChat() {

    }

    //스크랩 목록 클릭
    fun gotoScrapPage(){

    }

    //의뢰해요 클릭
    fun gotoRequestPage(){

    }

    //판매 작품 클릭
    fun gotoSalePage(){
        navigator.changePage(Page.PROFILE_SALE)
    }

    // 작업해요 클릭릭
    fun gotoWorkPage(){
        navigator.changePage(Page.PROFILE_WORK)
    }
}