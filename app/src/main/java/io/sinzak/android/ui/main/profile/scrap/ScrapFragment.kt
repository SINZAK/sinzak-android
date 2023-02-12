package io.sinzak.android.ui.main.profile.scrap

import android.view.View
import androidx.fragment.app.activityViewModels
import io.sinzak.android.databinding.FragmentScrapBinding
import io.sinzak.android.ui.base.BaseFragment

class ScrapFragment : BaseFragment() {

    private lateinit var bind : FragmentScrapBinding
    private val viewModel : ScrapViewModel by activityViewModels()

    override fun getFragmentRoot(): View {
        bind = FragmentScrapBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun navigateOnBackPressed() {
        viewModel.backPressed()
    }
}