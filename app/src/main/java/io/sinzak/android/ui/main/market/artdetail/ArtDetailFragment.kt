package io.sinzak.android.ui.main.market.artdetail

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.*
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.MainActivity

@AndroidEntryPoint
class ArtDetailFragment : BaseFragment() {

    lateinit var bind : FragmentArtDetailBinding

    private val contentViewModel by activityViewModels<ContentViewModel>()

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

        ArtistReportDialog(requireContext(),"김지호",{},{ArtistBlockDialog(requireContext(),{}).show()}).show()
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

            bind.llContent.addView(root)
        }
    }

    private fun inflateArtist(){
        ViewArtdetailArtistBinding.inflate(layoutInflater).apply{
            lifecycleOwner = viewLifecycleOwner

            bind.llContent.addView(root)
        }
    }

    private fun inflateSpec(){

        //todo : 표시할 자료 없으면 패스
        ViewArtdetailSpecBinding.inflate(layoutInflater).apply{
            lifecycleOwner = viewLifecycleOwner

            bind.llContent.addView(root)
        }
    }

    private fun inflateContent(){

        ViewArtdetailContentBinding.inflate(layoutInflater).apply{
            lifecycleOwner = viewLifecycleOwner

            bind.llContent.addView(root)
        }
    }

    private fun inflateBottom(){
        ViewArtdetailBottomBinding.inflate(layoutInflater).apply{
            lifecycleOwner = viewLifecycleOwner

            bind.flBottom.addView(root)
        }
    }
}