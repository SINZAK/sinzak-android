package io.sinzak.android.ui.login.name

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.RegisterPage
import io.sinzak.android.model.context.SignModel
import io.sinzak.android.model.navigate.RegisterNavigation
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.login.RegisterConnect
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NameViewModel @Inject constructor(
    val connect: RegisterConnect
    ) :
    BaseViewModel() {


    var typedName = ""
    private val _nameConfirmed = MutableStateFlow(false)
    val nameConfirmed: StateFlow<Boolean> get() = _nameConfirmed


    fun typeName(text: CharSequence) {
        _nameConfirmed.value = checkName(text)
        typedName = text.toString()
    }

    private fun checkName(text: CharSequence): Boolean {
        if (text.contains(' '))
            return false

        if (text.length > 12)
            return false

        text.forEach {
            if (it.code !in 0xAC00..0xD7AF && it.code !in 0x41..0x5A && it.code !in 0x61..0x7A && it !in "._-" && it !in "0123456789")
                return false

        }

        return true

    }

    fun onSubmit() {

        signModel.setUsername(typedName)

        connect.gotoCategoryPage()
    }

    fun onBackPressed(){
        connect.navigation.revealHistory()
    }
}