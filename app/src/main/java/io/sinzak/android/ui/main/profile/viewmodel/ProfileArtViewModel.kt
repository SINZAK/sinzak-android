package io.sinzak.android.ui.main.profile.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileArtViewModel @Inject constructor(
    private val model : ProfileModel
) : BaseViewModel() {
    val productsList get() = model.productList
    val worksList get() = model.workList
}