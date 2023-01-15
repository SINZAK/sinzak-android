package io.sinzak.android.ui.main.postwrite.fragment

import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentWriteWorkinfoBinding
import io.sinzak.android.ui.base.BaseFragment
import io.sinzak.android.ui.main.postwrite.viewmodels.ArtWorkViewModel

@AndroidEntryPoint
class ArtWorkFragment : BaseFragment() {

    private lateinit var bind : FragmentWriteWorkinfoBinding

    val viewModel by viewModels<ArtWorkViewModel>()


    override fun getFragmentRoot(): View {
        bind = FragmentWriteWorkinfoBinding.inflate(layoutInflater)
        return bind.root
    }


    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }

    override fun onFragmentCreated() {
        bind.apply{
            vm = viewModel
        }
    }

    override fun showBottomBar(): Boolean {
        return false
    }
}