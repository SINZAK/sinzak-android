package io.sinzak.android.ui.login.agreement

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.RegisterPage
import io.sinzak.android.model.navigate.RegisterNavigation
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.login.RegisterConnect
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@HiltViewModel
class AgreementViewModel @Inject constructor(
    val connect: RegisterConnect
    ) : BaseViewModel() {

    fun onSubmit() {
        signModel.termMarketing = checkMarketing.value
        connect.gotoDisplayNamePage()
    }

    fun onBackPressed(){
        connect.navigation.revealHistory()
    }



    val checkAge = MutableStateFlow(false)
    val checkRequire = MutableStateFlow(false)
    val checkPersonal = MutableStateFlow(false)
    val checkMarketing = MutableStateFlow(false)


    fun toggleAge() {
        checkAge.value = !checkAge.value
    }

    fun toggleAll() {
        if (checkAge.value && checkAge.value && checkPersonal.value && checkMarketing.value) {
            toggleAge()
            toggleMarketing()
            togglePersonal()
            toggleRequire()
        } else {
            checkAge.value = true
            checkRequire.value = true
            checkPersonal.value = true
            checkMarketing.value = true
        }
    }


    fun toggleRequire() {
        checkRequire.value = !checkRequire.value
    }


    fun togglePersonal() {
        checkPersonal.value = !checkPersonal.value
    }


    fun toggleMarketing() {
        checkMarketing.value = !checkMarketing.value
    }



}