package io.sinzak.android.ui.main.profile.edit

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentEditProfileBinding
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.ui.base.BaseFragment

@AndroidEntryPoint
class EditFragment : BaseFragment() {

    private lateinit var bind : FragmentEditProfileBinding

    private val viewModel : EditViewModel by activityViewModels()

    override fun getFragmentRoot(): View {
        bind = FragmentEditProfileBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply {
            bind.lifecycleOwner = viewLifecycleOwner
            vm = viewModel

            viewModel.connect.registerActivity(
                requireActivity() as BaseActivity<*>,
                this
            )

            viewModel.setInterestText(bind.tvCategory)

        }
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun navigateOnBackPressed() {
        viewModel.onBackPressed()
    }


}