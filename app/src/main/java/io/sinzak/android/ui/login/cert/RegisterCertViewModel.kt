package io.sinzak.android.ui.login.cert

import android.content.Context
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.certify.CertifyModel
import io.sinzak.android.model.insets.SoftKeyModel
import io.sinzak.android.ui.login.RegisterConnect
import io.sinzak.android.ui.main.profile.certification.CertificationViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class RegisterCertViewModel @Inject constructor(
    val connect: RegisterConnect,
    val softKeyModel : SoftKeyModel
) : CertificationViewModel(
    certifyModel = CertifyModel(connect.context),
    soft = softKeyModel
) {

    override val isPartOfSignup: Boolean = true

    override fun onSubmit(){

        signModel.setUniv(school!!)
        connect.gotoCertPage()

    }
    lateinit private var loginJob: Job


    override fun onCancel() {
        loginJob = signModel.isLogin.onEach {
            if(it) connect.gotoWelcome()
        }.launchIn(viewModelScope)
        signModel.join()
    }


}