package io.sinzak.android.ui.login.cert

import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import io.sinzak.android.ui.main.profile.certification.CertificationFragment
import io.sinzak.android.ui.main.profile.certification.CertificationViewModel

@AndroidEntryPoint
class RegisterCertFragment : CertificationFragment() {

    override val viewModel: CertificationViewModel
        by viewModels<RegisterCertViewModel>()




}