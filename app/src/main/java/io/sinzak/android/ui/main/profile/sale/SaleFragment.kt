package io.sinzak.android.ui.main.profile.sale

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentSaleBinding
import io.sinzak.android.ui.base.BaseFragment

@AndroidEntryPoint
class SaleFragment : BaseFragment() {

    private lateinit var bind : FragmentSaleBinding

    private val viewModel : SaleViewModel by activityViewModels()

    override fun getFragmentRoot(): View {
        bind = FragmentSaleBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply {
            bind.lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            fg = this@SaleFragment
        }
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }

    override fun showBottomBar(): Boolean {
        return false
    }

}