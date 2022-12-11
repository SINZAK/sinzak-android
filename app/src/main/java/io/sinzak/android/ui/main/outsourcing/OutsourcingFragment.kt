package io.sinzak.android.ui.main.outsourcing

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentOutsourcingBinding
import io.sinzak.android.databinding.ViewOutsourcingArtistBinding
import io.sinzak.android.databinding.ViewOutsourcingClientBinding
import io.sinzak.android.databinding.ViewOutsourcingFilterBinding
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.outsourcing.viewmodel.ArtistViewModel
import io.sinzak.android.ui.main.outsourcing.viewmodel.ClientViewModel
import io.sinzak.android.ui.main.outsourcing.viewmodel.OutsourcingViewModel


@AndroidEntryPoint
class OutsourcingFragment : BaseFragment(){


    private lateinit var bind : FragmentOutsourcingBinding

    private val filterViewModel : FilterViewModel by activityViewModels()
    private val viewModel : OutsourcingViewModel by activityViewModels()
    private val clientViewModel : ClientViewModel by activityViewModels()
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
            fg = this@OutsourcingFragment
            vm = viewModel
        }

        inflateFilter()
        inflateClient()
    }


    fun inflateFilter(){
        ViewOutsourcingFilterBinding.inflate(layoutInflater).apply{

            vm = filterViewModel
            lifecycleOwner = viewLifecycleOwner
            bind.flFilter.addView(root)
        }
    }

    fun inflateClient(){
        if(viewModel.isClientList.value)
            return
        bind.flOutsourcing.removeAllViews()
        viewModel.isClientList.value = true
        ViewOutsourcingClientBinding.inflate(layoutInflater).apply{
            vm = clientViewModel
            lifecycleOwner = viewLifecycleOwner
            bind.flOutsourcing.addView(root)
        }
    }

    fun inflateArtist(){
        if(!viewModel.isClientList.value)
            return
        bind.flOutsourcing.removeAllViews()
        viewModel.isClientList.value = false
        ViewOutsourcingArtistBinding.inflate(layoutInflater).apply{
            vm = artistViewModel
            lifecycleOwner = viewLifecycleOwner
            bind.flOutsourcing.addView(root)
        }
    }
}