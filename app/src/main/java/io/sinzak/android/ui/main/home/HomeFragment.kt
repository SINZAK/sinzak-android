package io.sinzak.android.ui.main.home

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.R
import io.sinzak.android.databinding.*
import io.sinzak.android.system.LogDebug
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.home.viewmodel.ArtMarketViewModel
import io.sinzak.android.ui.main.home.viewmodel.ArtReferViewModel
import io.sinzak.android.ui.main.home.viewmodel.ArtistViewModel
import io.sinzak.android.ui.main.home.viewmodel.HomeViewModel


@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var bind : FragmentHomeBinding

    private val viewModel : HomeViewModel by activityViewModels()

    private val artReferViewModel by activityViewModels<ArtReferViewModel>()
    private val artMarketViewModel by activityViewModels<ArtMarketViewModel>()
    private val artistViewModel : ArtistViewModel by activityViewModels()

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
        inflateBanner()
        inflateArtReferView()
        inflateArtOnmarketView()
        inflateArtistView()

    }

    private fun inflateBanner(){
        DataBindingUtil.inflate<ViewHomeBannerBinding>(layoutInflater,R.layout.view_home_banner,null,true).apply{
            lifecycleOwner = viewLifecycleOwner

            bind.llMain.addView(root)

        }
    }

    private fun inflateAppbar(){
        DataBindingUtil.inflate<ViewMainTopAppbarBinding>(layoutInflater, R.layout.view_main_top_appbar,null,false).apply{
            lifecycleOwner = viewLifecycleOwner
            LogDebug(javaClass.name,"INFLATE APPBAR")
            bind.flAppbar.addView(root)
        }

    }

    private fun inflateArtReferView(){
        DataBindingUtil.inflate<ViewHomeArtReferBinding>(layoutInflater,R.layout.view_home_art_refer,null,true).apply{
            vm = artReferViewModel
            fg = this@HomeFragment
            lifecycleOwner = viewLifecycleOwner
            bind.llMain.addView(root)
        }
    }

    private fun inflateArtOnmarketView(){
        DataBindingUtil.inflate<ViewHomeArtOnmarketBinding>(layoutInflater,R.layout.view_home_art_onmarket,null,true).apply{
            lifecycleOwner = viewLifecycleOwner
            vm = artMarketViewModel
            fg = this@HomeFragment
            bind.llMain.addView(root)
        }
    }

    private fun inflateArtistView(){
        DataBindingUtil.inflate<ViewHomeArtistBinding>(layoutInflater,R.layout.view_home_artist,null,true).apply{
            lifecycleOwner = viewLifecycleOwner
            vm = artistViewModel
            fg = this@HomeFragment
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