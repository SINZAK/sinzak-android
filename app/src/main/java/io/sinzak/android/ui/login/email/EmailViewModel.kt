package io.sinzak.android.ui.login.email

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.certify.CertifyModel
import io.sinzak.android.model.context.SignModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.login.RegisterConnect
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EmailViewModel @Inject constructor(
    private val certifyModel: CertifyModel,
    val connect: RegisterConnect

) : BaseViewModel() {






    fun onBackPressed(){
        connect.navigation.revealHistory()
    }

    fun sendEmail()
    {
        emailAddressAvailable.value = EMAIL_AVAILABLE
        certifyModel.setAddress(email)
        certifyModel.setUniv(signModel.getUniv())
        certifyModel.sendMailCode()

        certifyModel.flagSendSuccess.onEach {
            if(it)
                certRequested.value = true
        }.launchIn(viewModelScope)
    }



    fun join(){
        signModel.join()
    }


    fun onFinishRegister(){
        connect.gotoWelcome()
        connect.navigation.clearHistory()
    }

    fun checkCode(){
        if(codeAvailable.value == CERT_OK){
            onFinishRegister()
            return
        }
        certifyModel.setCode(code)
        certifyModel.checkMailCode()
    }


    operator fun invoke(): EmailViewModel{
        useFlag(certifyModel.flagCodeFailed){
            onCodeFailed()
        }
        useFlag(certifyModel.flagCodeSuccess){
            onCodeSuccess()
        }



        return this
    }


    private fun onCodeFailed(){
        codeAvailable.value = CERT_ERROR
    }

    private fun onCodeSuccess(){
        codeAvailable.value = CERT_OK

    }

    private fun onEmailError(){
        emailAddressAvailable.value = EMAIL_ERROR
    }







    var email = ""
        set(value) {
            field = value
            checkEmail()
        }

    private fun checkEmail(){
        if(email.contains('@') && email.contains(signModel.getUnivAddress()))
            emailAddressAvailable.value = EMAIL_AVAILABLE
        else
            onEmailError()

    }

    var code = ""
    set(value) {
        field = value
        checkCode()
    }


    val emailAddressAvailable = MutableStateFlow(NONE)
    val certRequested = MutableStateFlow(false)
    val codeAvailable = MutableStateFlow(NONE)







    companion object{
        const val NONE = 0
        const val EMAIL_ERROR = -1
        const val EMAIL_AVAILABLE = 1
        const val CERT_OK = 1
        const val CERT_ERROR = -1
    }





}