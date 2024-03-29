package io.sinzak.android.ui.main.profile.report

import android.widget.TextView
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.enums.Page
import io.sinzak.android.model.market.ProductDetailModel
import io.sinzak.android.model.profile.UserCommandModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ReportSendViewModel @Inject constructor(
    val productModel: ProductDetailModel,
    private val commandModel: UserCommandModel
    ) : BaseViewModel() {

    /**
     * 신고 사유를 입력받는 공간
     */
    private val _report = MutableStateFlow("")
    val report : StateFlow<String> get() = _report

    /**
     * 신고 타입을 저장하는 공간
     */
    val reportType = MutableStateFlow("")
    val userName get() = commandModel.reportName

    /************************************************
     * 입력을 받습니다
     ***************************************/

    /**
     * 신고 사유를 입력받습니다
     */
    fun reportInputText(cs : CharSequence) {
        cs.toString().let {
            if (_report.value != it) {
                _report.value = it
            }
        }
    }


    /************************************************
     * 클릭 시 실행
     ***************************************/

    /**
     * 신고 이유 선택시
     */
    fun selectType(textView: TextView){
        reportType.value = textView.text.toString()
        navigation.changePage(Page.PROFILE_REPORT_SEND)
    }


    /**
     * 신고하기 버튼 클릭시
     */
    fun onSubmit()
    {
        val reason = reportType.value + ", " + _report.value

        commandModel.reportUser(reason = reason)
        useFlag(commandModel.reportSuccessFlag){
            uiModel.showToast(valueModel.getString(R.string.str_accept_report))
            initState()
            navigation.removeHistory(Page.PROFILE_REPORT_SEND)
            navigation.removeHistory(Page.PROFILE_REPORT_TYPE)
            navigation.revealHistory()
        }
    }

    /**
     * 뒤로가기 클릭
     */
    fun onBackPressed()
    {
        _report.value = ""
        navigation.revealHistory()
    }


    private fun initState()
    {
        _report.value = ""
        reportType.value = ""
    }


}