package io.sinzak.android.ui.login.email

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.context.SignModel
import io.sinzak.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class EmailViewModel @Inject constructor(
    val signModel: SignModel
) : BaseViewModel() {


    fun onSubmit()
    {

    }
}