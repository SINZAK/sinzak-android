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
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.profile.follow.FollowViewModel
import io.sinzak.android.ui.main.profile.viewmodel.ProfileArtViewModel
import io.sinzak.android.ui.main.profile.viewmodel.ProfileViewModel
import javax.inject.Inject

@AndroidEntryPoint
open class ProfileFragment : BaseFragment() {

    private lateinit var bind : FragmentProfileBinding

    protected val viewModel by activityViewModels<ProfileViewModel>()
    private val artViewModel by activityViewModels<ProfileArtViewModel>()
    private val followViewModel by activityViewModels<FollowViewModel>()

    @Inject
    lateinit var connect: ProfileConnect

    override fun onResume() {
        super.onResume()
        viewModel.registerConnect(connect)
    }

    override fun getFragmentRoot(): View {
        bind = FragmentProfileBinding.inflate(layoutInflater)
        bind.lifecycleOwner = viewLifecycleOwner
        return bind.root
    }

    override fun onFragmentCreated() {
        viewModel.getMyProfileRemote()
        viewModel.postFcmToken()
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
    /**
     * 내 프로필 뷰 인플레이팅
     */
    private fun inflateChild() {
        inflateAppbar(showBackIc = false, myProfile = true)
        inflateMyProfile(myProfile = true)
        inflateLinkList()
        inflateArtSale()
        inflateArtWork()
    }

    /**
     * 타인 프로필의 뷰 인플레이팅
     */
    protected fun inflateOtherChild(){
        inflateAppbar(showBackIc = true, myProfile = false)
        inflateMyProfile(myProfile = false)
        inflateArtSale()
        inflateArtWork()
    }

    private fun inflateAppbar(showBackIc : Boolean, myProfile : Boolean){
        ViewProfileTopAppbarBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            showBack = showBackIc
            isMyProfile = myProfile
            bind.flAppbar.addView(root)
        }
    }
    private fun inflateMyProfile(myProfile : Boolean){
        ViewProfileMyprofileBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            fVm = followViewModel
            isMyProfile = myProfile
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
            vm = artViewModel
            pVm = viewModel
            bind.llProfiles.addView(root)
        }
    }
    private fun inflateArtWork() {
        ViewProfileArtWorkBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = artViewModel
            pVm = viewModel
            bind.llProfiles.addView(root)
        }
    }

}