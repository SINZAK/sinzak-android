package io.sinzak.android.ui.login

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.context.SignModel
import io.sinzak.android.model.navigate.RegisterNavigation
import io.sinzak.android.ui.base.BaseViewModel
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(val regNav : RegisterNavigation) : BaseViewModel() {


    val topPage = regNav.topPage
}