package io.sinzak.android.ui.main.profile.report

import android.os.Bundle
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.constants.CODE_USER_REPORT_ID
import io.sinzak.android.enums.Page
import io.sinzak.android.enums.ReportType
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.model.profile.UserCommandModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportSendViewModel @Inject constructor(
    val productModel: ProductDetailModel,
    private val commandModel: UserCommandModel
    ) : BaseViewModel() {

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
            if (_report.value != it) {
                _report.value = it
            }
        }
    }

    fun isFromProfile(boolean: Boolean) {
        _isFromProfile.value = boolean
    }

    /**
     * 신고 이유 선택시
     */
    fun goToReportSendPage(type: ReportType){
        _reportType.value = type
        navigation.changePage(Page.PROFILE_REPORT_SEND)
    }
    /**
     * 신고 이유 제출
     */
    private fun makeReason(type: ReportType) : String
    {
        val reportType =
        when(type)
        {
            ReportType.REPORT_SELLER -> R.string.str_report_type_seller
            ReportType.REPORT_NO_MANNER -> R.string.str_report_type_no_manner
            ReportType.REPORT_SEXUAL -> R.string.str_report_type_sexual
            ReportType.REPORT_DISPUTE -> R.string.str_report_type_dispute
            ReportType.REPORT_CHEAT -> R.string.str_report_type_cheat
            ReportType.REPORT_OTHER -> R.string.str_report_type_other
        }

        return reportType.toString() + ", " + _report.value

    }

    /**
     * 완료 버튼 클릭시
     */
    fun onSubmit()
    {
        commandModel.apply {
        setReason(
            makeReason(_reportType.value)
        )
        reportUser(profileModel.currentUserId.value)
        }
    }
}