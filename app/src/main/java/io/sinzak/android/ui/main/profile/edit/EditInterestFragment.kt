package io.sinzak.android.ui.main.profile.edit

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.ui.login.interest.InterestFragment
import io.sinzak.android.ui.login.interest.InterestViewModel

@AndroidEntryPoint
class EditInterestFragment : InterestFragment() {

    override val viewModel: InterestViewModel
        by viewModels<EditInterestViewModel>()

    override fun navigateOnBackPressed() {
        viewModel.onBackPressed()
    }
}