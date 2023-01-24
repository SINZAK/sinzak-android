package io.sinzak.android.ui.login.cert

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.certify.CertifyModel
import io.sinzak.android.ui.login.RegisterConnect
import io.sinzak.android.ui.main.profile.certification.CertificationViewModel
import javax.inject.Inject


@HiltViewModel
class RegisterCertViewModel @Inject constructor(
    val connect: RegisterConnect
) : CertificationViewModel(
    certifyModel = CertifyModel()
) {

    override val isPartOfSignup: Boolean = true

    override fun onSubmit(){

        signModel.setUniv(school!!)
        connect.gotoCertPage()

    }

    override fun onCancel() {
        connect.gotoWelcome()
    }


}