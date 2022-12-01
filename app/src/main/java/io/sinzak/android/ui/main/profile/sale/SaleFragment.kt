package io.sinzak.android.ui.main.profile.sale

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.R
import io.sinzak.android.databinding.FragmentSaleBinding
import io.sinzak.android.databinding.ViewSaleTopAppbarBinding
import io.sinzak.android.ui.base.BaseFragment

@AndroidEntryPoint
class SaleFragment : BaseFragment() {

    private lateinit var bind : FragmentSaleBinding

    private val viewModel : SaleViewModel by activityViewModels()

    override fun getFragmentRoot(): View {
        bind = FragmentSaleBinding.inflate(layoutInflater)
        bind.vm = viewModel
        bind.lifecycleOwner = viewLifecycleOwner
        return bind.root
    }

    override fun onFragmentCreated() {
        inflateChild()
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    /**************v
     *
     * VIEW INLFATING
     *
     ***************/

    private fun inflateChild() {
        inflateAppbar()
    }

    private fun inflateAppbar() {
        DataBindingUtil.inflate<ViewSaleTopAppbarBinding>(layoutInflater, R.layout.view_sale_top_appbar,null,false).apply {
            lifecycleOwner = viewLifecycleOwner
            fg = this@SaleFragment
            bind.flAppbar.addView(root)
        }
    }
}