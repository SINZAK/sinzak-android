package io.sinzak.android.ui.login.email

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.context.CertifyModel
import io.sinzak.android.model.context.SignModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.login.RegisterConnect
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlin.math.sign

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
        certifyModel.setUnvi(signModel.getUnivName())
        certifyModel.sendMailCode()
    }

    fun join(){
        signModel.join()
    }

    fun checkCode(){
        certifyModel.setCode(code)
        certifyModel.checkMailCode()
    }


    init{
        useFlag(certifyModel.flagCodeFailed){
            onCodeFailed()
        }
        useFlag(certifyModel.flagCodeSuccess){
            onCodeSuccess()
        }
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