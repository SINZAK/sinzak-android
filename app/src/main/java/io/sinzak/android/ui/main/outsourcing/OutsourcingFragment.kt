package io.sinzak.android.ui.main.outsourcing

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentOutsourcingBinding
import io.sinzak.android.databinding.ViewOutsourcingArtistBinding
import io.sinzak.android.databinding.ViewOutsourcingFilterBinding
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.outsourcing.viewmodel.ArtistViewModel
import io.sinzak.android.ui.main.outsourcing.viewmodel.OutsourcingViewModel


@AndroidEntryPoint
class OutsourcingFragment : BaseFragment(){


    private lateinit var bind : FragmentOutsourcingBinding

    private val filterViewModel : FilterViewModel by activityViewModels()
    private val viewModel : OutsourcingViewModel by activityViewModels()
    private val artistViewModel : ArtistViewModel by activityViewModels()

    override fun getFragmentRoot(): View {
        bind = FragmentOutsourcingBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun navigateOnBackPressed() {

    }

    override fun showBottomBar(): Boolean {
        return true
    }

    override fun onFragmentCreated() {
        bind.apply{
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }

        inflateFilter()
        inflateArtist()
    }


    private fun inflateFilter(){
        ViewOutsourcingFilterBinding.inflate(layoutInflater).apply{

            vm = filterViewModel
            lifecycleOwner = viewLifecycleOwner
            bind.flFilter.addView(root)
        }
    }
    private fun inflateArtist(){
        ViewOutsourcingArtistBinding.inflate(layoutInflater).apply{
            vm = artistViewModel
            lifecycleOwner = viewLifecycleOwner
            bind.flOutsourcing.addView(root)
        }
    }
}