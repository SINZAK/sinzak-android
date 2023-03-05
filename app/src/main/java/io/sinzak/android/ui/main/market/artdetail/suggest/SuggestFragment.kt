package io.sinzak.android.ui.main.market.artdetail.suggest

import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentArtdetailSuggestBinding
import io.sinzak.android.ui.base.BaseFragment

@AndroidEntryPoint
class SuggestFragment : BaseFragment() {

    private val viewModel by viewModels<SuggestViewModel>()



    lateinit var bind : FragmentArtdetailSuggestBinding
    override fun getFragmentRoot(): View {

        bind = FragmentArtdetailSuggestBinding.inflate(layoutInflater)
        return bind.root

    }

    override fun navigateOnBackPressed() {
        viewModel.onBackPressed()
    }

    override fun onFragmentCreated() {
        bind.apply{
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
    }

    override fun showBottomBar(): Boolean {
        return false
    }
}