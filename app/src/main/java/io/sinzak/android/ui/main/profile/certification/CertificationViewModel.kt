package io.sinzak.android.ui.main.profile.certification

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.RegisterPage
import io.sinzak.android.model.context.SignModel
import io.sinzak.android.model.navigate.RegisterNavigation
import io.sinzak.android.remote.dataclass.local.SchoolData
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.certification.adapter.CertificationAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CertificationViewModel @Inject constructor(val regNav: RegisterNavigation) : BaseViewModel() {

    private val _schoolInput = MutableStateFlow("")
    val schoolInput: StateFlow<String> get() = _schoolInput

    private val _showSchoolList = MutableStateFlow(false)
    val showSchoolList: StateFlow<Boolean> get() = _showSchoolList

    var school: SchoolData? = null
    val schoolInserted = MutableStateFlow(false)

    val certificationAdapter = CertificationAdapter {
        _schoolInput.value = it.schoolName
        school = it
        schoolInserted.value = true
    }


    fun schoolInputText(cs: CharSequence) {
        cs.toString().let { text ->
            if (_schoolInput.value != text) {
                _schoolInput.value = text
            }

            certificationAdapter.setSchoolList(
                signModel.univList.filter { it.schoolName.contains(text) }.toList()
            )

            _showSchoolList.value = text != ""
        }
    }


    fun onSubmit() {
        signModel.setUniv(school!!)
        regNav.changePage(RegisterPage.PAGE_UNIVERSITY_CERT)
    }
}