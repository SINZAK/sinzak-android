package io.sinzak.android.ui.main.profile.certification

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.RegisterPage
import io.sinzak.android.model.navigate.RegisterNavigation
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CertificationViewModel @Inject constructor(val regNav : RegisterNavigation) : BaseViewModel() {

    private val _isGraduate = MutableStateFlow(false)
    val isGraduate : StateFlow<Boolean> get() = _isGraduate

    fun changeAcademicState(state: Boolean) {
        _isGraduate.value = state
    }


    fun onSubmit(){
        regNav.changePage(RegisterPage.PAGE_UNIVERSITY_CERT)
    }
}