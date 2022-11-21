package io.sinzak.android.ui.main.market

import android.view.View
import androidx.databinding.DataBindingUtil
import io.sinzak.android.R
import io.sinzak.android.databinding.FragmentMarketBinding
import io.sinzak.android.databinding.ViewMainTopAppbarBinding
import io.sinzak.android.databinding.ViewMarketArtsBinding
import io.sinzak.android.databinding.ViewMarketFilterBinding
import io.sinzak.android.system.LogDebug
import io.sinzak.android.ui.base.BaseFragment

class MarketFragment : BaseFragment() {

    private lateinit var bind : FragmentMarketBinding


    override fun getFragmentRoot(): View {
        bind = FragmentMarketBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun navigateOnBackPressed() {

    }

    override fun onFragmentCreated() {
        inflateChild()
    }

    override fun showBottomBar(): Boolean {
        return true
    }


    /**************v
     *
     * VIEW INLFATING
     *
     ***************/

    private fun inflateChild(){
        inflateArts()
        inflateAppbar()
        inflateFilter()
    }

    private fun inflateAppbar(){
        DataBindingUtil.inflate<ViewMainTopAppbarBinding>(layoutInflater, R.layout.view_main_top_appbar,null,false).apply{
            lifecycleOwner = viewLifecycleOwner
            LogDebug(javaClass.name,"INFLATE APPBAR")
            bind.flAppbar.addView(root)
        }
    }

    private fun inflateFilter(){
        ViewMarketFilterBinding.inflate(layoutInflater).apply{
            lifecycleOwner = viewLifecycleOwner
            bind.flFilter.addView(root)
        }
    }

    private fun inflateArts(){
        ViewMarketArtsBinding.inflate(layoutInflater).apply{
            lifecycleOwner = viewLifecycleOwner

            bind.flArts.addView(root)
        }
    }
}