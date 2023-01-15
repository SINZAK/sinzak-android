package io.sinzak.android.ui.login.welcome

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.login.RegisterConnect
import javax.inject.Inject


@HiltViewModel
class WelcomeViewModel @Inject constructor(
    val connect: RegisterConnect
) : BaseViewModel(){
    fun done(){
        connect.finishPage()
    }
}