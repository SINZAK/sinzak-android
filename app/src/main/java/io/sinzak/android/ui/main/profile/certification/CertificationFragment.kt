package io.sinzak.android.ui.main.profile.certification

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentCertificationBinding
import io.sinzak.android.enums.Page
import io.sinzak.android.ui.base.BaseFragment
import javax.inject.Inject

@AndroidEntryPoint
open class CertificationFragment : BaseFragment() {

    private lateinit var bind : FragmentCertificationBinding

    open val viewModel : CertificationViewModel by activityViewModels()


    override fun getFragmentRoot(): View {
        bind = FragmentCertificationBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply {
            bind.lifecycleOwner = viewLifecycleOwner
            vm = viewModel
        }
        viewModel.clearAllState()
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun navigateOnBackPressed() {
        viewModel.onCancel()
    }


}