package io.sinzak.android.ui.main.profile.follow

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentFollowBinding
import io.sinzak.android.ui.base.BaseFragment

@AndroidEntryPoint
class FollowFragment : BaseFragment() {

    private lateinit var bind : FragmentFollowBinding

    private val viewModel : FollowViewModel by activityViewModels()

    override fun getFragmentRoot(): View {
        bind = FragmentFollowBinding.inflate(layoutInflater)
        bind.apply {
            lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
        return bind.root
    }

    override fun onFragmentCreated() {
        viewModel.changeTap(viewModel.tap.value)
    }


    override fun showBottomBar(): Boolean {
        return false
    }

    override fun navigateOnBackPressed() {
        viewModel.onBackPressed()
    }
}