package io.sinzak.android.ui.main.home

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.R
import io.sinzak.android.databinding.FragmentHomeBinding
import io.sinzak.android.databinding.ViewHomeArtOnmarketBinding
import io.sinzak.android.databinding.ViewHomeArtReferBinding
import io.sinzak.android.databinding.ViewMainTopAppbarBinding
import io.sinzak.android.system.LogDebug
import io.sinzak.android.ui.base.BaseFragment


@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var bind : FragmentHomeBinding

    private val viewModel : HomeViewModel by activityViewModels()

    private val artReferViewModel by activityViewModels<ArtReferViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentHomeBinding.inflate(layoutInflater)
        return bind.root
    }


    override fun onFragmentCreated() {
        bind.apply{
            fragment = this@HomeFragment
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        LogDebug(javaClass.name,"FRAGMENT_CREATED")

        inflateAppbar()
        inflateArtReferView()
        inflateArtOnmarketView()
    }

    private fun inflateAppbar(){
        DataBindingUtil.inflate<ViewMainTopAppbarBinding>(layoutInflater, R.layout.view_main_top_appbar,null,false).apply{
            lifecycleOwner = viewLifecycleOwner
            LogDebug(javaClass.name,"INFLATE APPBAR")
            bind.flAppbar.addView(root)
        }

    }

    private fun inflateArtReferView(){
        ViewHomeArtReferBinding.inflate(layoutInflater).apply{
            vm = artReferViewModel
            fg = this@HomeFragment
            lifecycleOwner = viewLifecycleOwner
            bind.llMain.addView(root)
        }
    }

    private fun inflateArtOnmarketView(){
        ViewHomeArtOnmarketBinding.inflate(layoutInflater).apply{
            lifecycleOwner = viewLifecycleOwner
            bind.llMain.addView(root)
        }
    }
    override fun showBottomBar(): Boolean {
        return true
    }

    override fun navigateOnBackPressed() {
        //navigator.changePage()
    }
}