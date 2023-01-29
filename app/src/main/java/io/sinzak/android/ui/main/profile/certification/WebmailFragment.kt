package io.sinzak.android.ui.main.profile.certification

import android.view.View
import androidx.fragment.app.activityViewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.databinding.FragmentWebmailBinding
import io.sinzak.android.databinding.ViewWebmailSchoolidBinding
import io.sinzak.android.databinding.ViewWebmailSchoolmailBinding
import io.sinzak.android.ui.base.BaseActivity
import io.sinzak.android.ui.base.BaseFragment

@AndroidEntryPoint
class WebmailFragment : BaseFragment() {

    private lateinit var bind: FragmentWebmailBinding

    private val viewModel : WebmailViewModel by activityViewModels()

    override fun getFragmentRoot(): View {
        bind = FragmentWebmailBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onFragmentCreated() {
        bind.apply {
            bind.lifecycleOwner = viewLifecycleOwner
            vm = viewModel
            fg = this@WebmailFragment
        }

        viewModel.connect.registerActivity(requireActivity() as BaseActivity<*>)

        inflateSchoolMail()
        inflateSchoolId()

    }

    private fun inflateSchoolMail(){
        ViewWebmailSchoolmailBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            bind.flWebMail.addView(root)
            vm = viewModel
        }
    }

    private fun inflateSchoolId() {
        ViewWebmailSchoolidBinding.inflate(layoutInflater).apply {
            lifecycleOwner = viewLifecycleOwner
            bind.flStudentId.addView(root)
            vm = viewModel
        }
    }

    override fun showBottomBar(): Boolean {
        return false
    }

    override fun navigateOnBackPressed() {
        navigator.revealHistory()
        viewModel.clearAllState()
    }

}