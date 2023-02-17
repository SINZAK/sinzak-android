package io.sinzak.android.ui.main.profile.edit

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.login.RegisterConnect
import io.sinzak.android.ui.login.interest.InterestViewModel
import javax.inject.Inject

@HiltViewModel
class EditInterestViewModel @Inject constructor(
    val registerConnect: RegisterConnect
) : InterestViewModel(
    connect = registerConnect
) {

    override val isPartOfRegister: Boolean = false

    override fun onSubmit() {
        navigation.revealHistory()
    }

    override fun onBackPressed() {
        navigation.revealHistory()
    }
}