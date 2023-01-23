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
import io.sinzak.android.system.LogDebug
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.profile.viewmodel.ProfileSaleViewModel
import io.sinzak.android.ui.main.profile.viewmodel.ProfileViewModel
import io.sinzak.android.ui.main.profile.viewmodel.ProfileWorkViewModel
import javax.inject.Inject

@AndroidEntryPoint
open class ProfileFragment : BaseFragment() {

    private lateinit var bind : FragmentProfileBinding

    protected val viewModel by activityViewModels<ProfileViewModel>()
    private val profileSaleViewModel by activityViewModels<ProfileSaleViewModel>()
    private val profileWorkViewModel by activityViewModels<ProfileWorkViewModel>()

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
        LogDebug(javaClass.name,"내 프로필 실행")
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
        inflateAppbar(false)
        inflateMyProfile()
        inflateLinkList()
        inflateArtSale()
        inflateArtWork()
    }

    /**
     * 타인 프로필의 뷰 인플레이팅
     */
    protected fun inflateOtherChild(){
        inflateAppbar(true)
        inflateMyProfile()
        inflateArtSale()
        inflateArtWork()
    }

    private fun inflateAppbar(showBackIc : Boolean){
        ViewProfileTopAppbarBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            fg = this@ProfileFragment
            showBack = showBackIc
            bind.flAppbar.addView(root)
        }
    }
    private fun inflateMyProfile(){
        ViewProfileMyprofileBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
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
            pVm = viewModel
            bind.llProfiles.addView(root)
        }
    }
    private fun inflateArtWork() {
        ViewProfileArtWorkBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            vm = profileWorkViewModel
            pVm = viewModel
            bind.llProfiles.addView(root)
        }
    }

}