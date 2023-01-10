package io.sinzak.android.ui.main.home

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.R
import io.sinzak.android.databinding.*
import io.sinzak.android.enums.Page
import io.sinzak.android.system.LogDebug
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.home.viewmodel.*


@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private lateinit var bind : FragmentHomeBinding

    private val viewModel : HomeViewModel by activityViewModels()

    private val artReferViewModel by activityViewModels<ArtReferViewModel>()
    private val artMarketViewModel by activityViewModels<ArtMarketViewModel>()
    private val artRecentViewModel by activityViewModels<ArtRecentViewModel>()
    private val artistViewModel : ArtistViewModel by activityViewModels()
    private val categoryViewModel : ArtCategoryViewModel by activityViewModels()

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

        if(viewModel.signModel.isLogin())
        {
            inflateArtOnmarketView()
            inflateArtistView()
            inflateRecentView()
            inflateCategory()
        }
        else{
            inflateRecentView()
            inflateArtReferView()
            inflateArtOnmarketView()
            inflateCategory()
        }



        viewModel.getProducts()

    }

    fun gotoNotification(){
        navigator.changePage(Page.HOME_NOTIFICATION)
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
            fg = this@HomeFragment
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

    private fun inflateRecentView(){
        DataBindingUtil.inflate<ViewHomeArtRecentBinding>(layoutInflater, R.layout.view_home_art_recent, null,true).apply{
            lifecycleOwner = viewLifecycleOwner
            vm = artRecentViewModel
            fg = this@HomeFragment
            bind.llMain.addView(root)
        }
    }

    private fun inflateCategory(){
        DataBindingUtil.inflate<ViewHomeLinearBinding>(layoutInflater, R.layout.view_home_linear, null, true).apply{
            lifecycleOwner = viewLifecycleOwner
            vm = categoryViewModel
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