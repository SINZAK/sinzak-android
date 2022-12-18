package io.sinzak.android.ui.main.postwrite.fragment

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentWriteArtinfoBinding
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.postwrite.viewmodels.InfoViewModel

@AndroidEntryPoint
class ArtInfoFragment : BaseFragment() {

    lateinit var bind : FragmentWriteArtinfoBinding
    val viewModel by activityViewModels<InfoViewModel>()

    override fun getFragmentRoot(): View {
        bind = FragmentWriteArtinfoBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun onFragmentCreated() {
        bind.lifecycleOwner = viewLifecycleOwner
        bind.fg = this
        bind.vm = viewModel
    }

}