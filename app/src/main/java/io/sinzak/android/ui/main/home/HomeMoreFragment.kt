package io.sinzak.android.ui.main.home

import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentHomeMoreBinding
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.home.viewmodel.HomeMoreViewModel


@AndroidEntryPoint
class HomeMoreFragment : BaseFragment() {


    private lateinit var bind : FragmentHomeMoreBinding

    private val viewModel by viewModels<HomeMoreViewModel>()



    override fun getFragmentRoot(): View {
        bind = FragmentHomeMoreBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }

    override fun onFragmentCreated() {
        bind.vm = viewModel
        bind.lifecycleOwner = viewLifecycleOwner
        bind.fg = this
    }

    override fun showBottomBar(): Boolean {
        return true
    }
}