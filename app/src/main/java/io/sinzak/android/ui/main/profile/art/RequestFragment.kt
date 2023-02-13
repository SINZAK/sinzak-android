package io.sinzak.android.ui.main.profile.art

import android.view.View
import androidx.fragment.app.activityViewModels
import io.sinzak.android.databinding.FragmentRequestBinding
import io.sinzak.android.ui.base.BaseFragment

class RequestFragment : BaseFragment() {

    private lateinit var bind : FragmentRequestBinding
    private val viewModel : RequestViewModel by activityViewModels()

    override fun getFragmentRoot(): View {
        bind = FragmentRequestBinding.inflate(layoutInflater)
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