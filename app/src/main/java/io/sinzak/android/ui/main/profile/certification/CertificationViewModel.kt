package io.sinzak.android.ui.main.profile.certification

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.RegisterPage
import io.sinzak.android.model.navigate.RegisterNavigation
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.certification.adapter.CertificationAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CertificationViewModel @Inject constructor(val regNav : RegisterNavigation) : BaseViewModel() {

    private val _schoolInput = MutableStateFlow("")
    val schoolInput : StateFlow<String> get() = _schoolInput

    private val _showSchoolList = MutableStateFlow(false)
    val showSchoolList : StateFlow<Boolean> get() = _showSchoolList

    val certificationAdapter = CertificationAdapter{
        _schoolInput.value = it
    }


    fun schoolInputText(cs: CharSequence) {
        cs.toString().let {
            if(_schoolInput.value != it) {
                _schoolInput.value = it
            }

            _showSchoolList.value = it != ""
        }
    }



    fun onSubmit(){
        regNav.changePage(RegisterPage.PAGE_UNIVERSITY_CERT)
    }
}