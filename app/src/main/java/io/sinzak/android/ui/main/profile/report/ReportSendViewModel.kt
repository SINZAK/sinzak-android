package io.sinzak.android.ui.main.profile.report

import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.constants.CODE_USER_REPORT_ID
import io.sinzak.android.enums.Page.*
import io.sinzak.android.enums.ReportType
import io.sinzak.android.model.market.MarketProductModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportSendViewModel @Inject constructor(val productModel: MarketProductModel) : BaseViewModel() {

    private val _report = MutableStateFlow("")
    val report : StateFlow<String> get() = _report

    private val _reportType = MutableStateFlow(ReportType.REPORT_SELLER)
    val reportType: StateFlow<ReportType> get() = _reportType

    val profile get() = profileModel.profile
    val art get() = productModel.art

    private val _isFromProfile = MutableStateFlow(true)
    val isFromProfile : StateFlow<Boolean> get() = _isFromProfile

    init{
        CoroutineScope(Dispatchers.Main).launch {
            subscribe()
        }
    }

    private fun subscribe(){
        invokeStateFlow(navigation.bundleInserted){
            navigation.getBundleData(this::class)?.apply{
                getExtra(this)
            }
        }
    }

    private fun getExtra(bundle : Bundle){
        bundle.getString(CODE_USER_REPORT_ID)?.let{
            isFromProfile(true)
        }
    }


    fun reportInputText(cs : CharSequence) {
        cs.toString().let {
            if(_report.value != it){
                _report.value = it
            }
        }
    }

    fun setReportType(type: ReportType) {
        _reportType.value = type
    }

    fun isFromProfile(boolean: Boolean) {
        _isFromProfile.value = boolean
    }
}