package io.sinzak.android.ui.main.profile.certification

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.R
import io.sinzak.android.enums.Page
import io.sinzak.android.model.certify.CertifyModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class VerifyViewModel @Inject constructor(
    val pModel : ProfileModel,
    val certifyModel: CertifyModel
) : BaseViewModel() {

    val profile get() = pModel.profile

    val linkInput = MutableStateFlow("")
    val buttonEnabled = MutableStateFlow(false)

    /************************************************
     * 입력을 받습니다
     ***************************************/

    /**
     * 포트폴리오 링크를 입력 받습니다
     */
    fun linkInputText(cs : CharSequence)
    {
        cs.toString().let {
            if (linkInput.value != it) linkInput.value = it
            buttonEnabled.value = it.isNotEmpty()
        }
    }

    /************************************************
     * 클릭 시 실행
     ***************************************/

    /**
     * 학교 인증하기 버튼을 누릅니다
     */
    fun gotoCertificationPage(hasCert : Boolean){
        if(!hasCert) navigation.changePage(Page.PROFILE_CERTIFICATION)
        else return
    }

    /**
     * 신청하기 버튼을 누릅니다
     */
    fun onSubmit(cert_author:Boolean, verifyProcess : Boolean)
    {
        if (cert_author){
            uiModel.showToast(valueModel.getString(R.string.str_already_certify))
            return
        }

        if (verifyProcess){
            uiModel.showToast(valueModel.getString(R.string.str_please_wait))
            return
        }

        certifyModel.requestCertifyPortfolio(linkInput.value.trim())
        useFlag(certifyModel.flagPortfolioSuccess){
            navigation.changePage(Page.PROFILE)
            navigation.clearHistory()
        }
    }

    /**
     * 뒤로가기 버튼을 누릅니다
     */
    fun onBackPressed()
    {
        navigation.revealHistory()
    }

}