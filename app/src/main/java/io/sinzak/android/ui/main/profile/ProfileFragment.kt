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
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.market.artdetail.ArtistBlockDialog
import io.sinzak.android.ui.main.market.artdetail.ArtistReportDialog
import io.sinzak.android.ui.main.profile.edit.EditViewModel
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
        DataBindingUtil.inflate<ViewProfileTopAppbarBinding>(layoutInflater, R.layout.view_profile_top_appbar,null, false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            fg = this@ProfileFragment
            bind.flAppbar.addView(root)
        }
    }
    private fun inflateMyProfile(){
        DataBindingUtil.inflate<ViewProfileMyprofileBinding>(layoutInflater,R.layout.view_profile_myprofile,null,false).apply {
            lifecycleOwner = viewLifecycleOwner
            pVm = viewModel
            eVm = editProfileViewModel
            fg = this@ProfileFragment
            bind.llProfiles.addView(root)
        }
    }
    private fun inflateLinkList(){
        DataBindingUtil.inflate<ViewProfileLinkListBinding>(layoutInflater,R.layout.view_profile_link_list,null,false).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            bind.llProfiles.addView(root)
        }
    }
    private fun inflateArtSale(){
        DataBindingUtil.inflate<ViewProfileArtSaleBinding>(layoutInflater,R.layout.view_profile_art_sale,null,true).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = profileSaleViewModel
            fg = this@ProfileFragment
            bind.llProfiles.addView(root)
        }
    }
    private fun inflateArtWork(){
        DataBindingUtil.inflate<ViewProfileArtWorkBinding>(layoutInflater,R.layout.view_profile_art_work,null,true).apply {
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
        ArtistReportDialog(requireContext(),"김지호",{}, { showBlockDialog() }).show()
    }

    // 차단하기 다이얼로그
    fun showBlockDialog(){
        ArtistBlockDialog(requireContext(),{}).show()
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