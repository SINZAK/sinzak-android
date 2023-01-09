package io.sinzak.android.ui.main.market.artdetail

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.*
import io.sinzak.android.enums.Page
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.MainActivity
import io.sinzak.android.ui.main.profile.report.ReportSendViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ArtDetailFragment : BaseFragment() , View.OnClickListener {

    lateinit var bind : FragmentArtDetailBinding

    private val contentViewModel by activityViewModels<ContentViewModel>()
    private val reportSendViewModel by activityViewModels<ReportSendViewModel>()

    @Inject
    lateinit var profileModel: ProfileModel

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



    fun showReportDialog(){

        ArtistReportDialog(requireContext(),contentViewModel.art.value!!.author,{ goToReportPage() },{ showBlockDialog() }).show()
    }

    // 차단하기 다이얼로그
    fun showBlockDialog(){
        ArtistBlockDialog(requireContext(),{/*차단 api*/}).show()
    }
    // 사용자 신고 페이지로
    fun goToReportPage(){
        reportSendViewModel.isFromProfile(false)
        navigator.changePage(Page.PROFILE_REPORT_TYPE)
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
            fg = this@ArtDetailFragment
            bind.llContent.addView(root)
        }
    }

    private fun inflateSpec(){

        //todo : 표시할 자료 없으면 패스
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

    override fun onClick(v: View?) {
        profileModel.getUserProfile("100")
        navigator.changePage(Page.PROFILE)
    }


}