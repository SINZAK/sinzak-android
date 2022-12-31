package io.sinzak.android.ui.main.profile.report

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ReportSendViewModel @Inject constructor() : BaseViewModel() {

    private val _report = MutableStateFlow("")
    val report : StateFlow<String> get() = _report

    fun typeInputText(cs : CharSequence) {
        cs.toString().let {
            if(_report.value != it){
                _report.value = it
            }
        }
    }
}