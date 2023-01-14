package io.sinzak.android.ui.main.market.artdetail

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.*
import io.sinzak.android.enums.Page
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.MainActivity
import io.sinzak.android.ui.main.market.artdetail.dialog.ArtistBlockDialog
import io.sinzak.android.ui.main.market.artdetail.dialog.ArtistReportDialog
import io.sinzak.android.ui.main.profile.report.ReportSendViewModel
import io.sinzak.android.ui.main.profile.viewmodel.ProfileViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ArtDetailFragment : BaseFragment(){

    lateinit var bind : FragmentArtDetailBinding

    @Inject
    lateinit var connect: ArtDetailConnect

    private val contentViewModel by activityViewModels<ContentViewModel>()
    private val profileViewModel by activityViewModels<ProfileViewModel>()

    override fun onResume() {
        super.onResume()
        contentViewModel.registerConnect(connect)
    }

    override fun getFragmentRoot(): View {
        bind = FragmentArtDetailBinding.inflate(layoutInflater)
        return bind.root
    }


    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }

    override fun onFragmentCreated() {
        bind.apply{
            activity = requireActivity() as MainActivity
            vm = contentViewModel
            fg = this@ArtDetailFragment
        }

        inflateBottom()
        inflateImage()
        inflateTitle()
        inflateArtist()
        inflateSpec()
        inflateContent()
    }

    override fun showBottomBar(): Boolean {
        return false
    }





    private fun inflateImage(){
        ViewArtdetailImageBinding.inflate(layoutInflater).apply{
            lifecycleOwner = viewLifecycleOwner
            vm = contentViewModel
            bind.llContent.addView(root)
        }
    }

    private fun inflateTitle(){
        ViewArtdetailTitleBinding.inflate(layoutInflater).apply{
            lifecycleOwner = viewLifecycleOwner
            vm = contentViewModel

            bind.llContent.addView(root)
        }
    }

    private fun inflateArtist(){
        ViewArtdetailArtistBinding.inflate(layoutInflater).apply{
            lifecycleOwner = viewLifecycleOwner
            vm = contentViewModel
            bind.llContent.addView(root)
        }
    }

    private fun inflateSpec(){
        ViewArtdetailSpecBinding.inflate(layoutInflater).apply{
            lifecycleOwner = viewLifecycleOwner
            vm = contentViewModel
            bind.llContent.addView(root)
        }
    }

    private fun inflateContent(){

        ViewArtdetailContentBinding.inflate(layoutInflater).apply{
            lifecycleOwner = viewLifecycleOwner
            vm = contentViewModel
            bind.llContent.addView(root)
        }
    }

    private fun inflateBottom(){
        ViewArtdetailBottomBinding.inflate(layoutInflater).apply{
            lifecycleOwner = viewLifecycleOwner
            vm = contentViewModel
            bind.flBottom.addView(root)
        }
    }




}