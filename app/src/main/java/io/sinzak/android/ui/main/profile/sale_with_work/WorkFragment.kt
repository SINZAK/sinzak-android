package io.sinzak.android.ui.main.profile.sale_with_work

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentWorkBinding
import io.sinzak.android.ui.base.BaseFragment

@AndroidEntryPoint
class WorkFragment : BaseFragment() {

    private lateinit var bind: FragmentWorkBinding

    private val viewModel : WorkViewModel by activityViewModels()

    override fun getFragmentRoot(): View {
        bind = FragmentWorkBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply {
            bind.lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            fg = this@WorkFragment
        }
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
    }
}