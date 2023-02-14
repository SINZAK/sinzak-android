package io.sinzak.android.ui.main.profile.certification

import android.text.TextUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.certify.CertifyModel
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.utils.FileUtil
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class WebmailViewModel @Inject constructor(
    private val certifyModel: CertifyModel,
    val connect: CertifyConnect
) : BaseViewModel() {

    /**
     * 현재 페이지, 웹메일 인증: false 학생증 인증 : true
     */
    val currentPage = MutableStateFlow(WEB_MAIL_PAGE)

    /**
     * 입력된 학교 메일을 저장
     */
    val webMailInput = MutableStateFlow("")

    /**
     * 학교메일 인증 상태
     */
    val webMailState = MutableStateFlow(INIT)

    /**
     * 입력된 코드를 저장
     */
    val codeInput = MutableStateFlow("")

    /**
     * 인증 코드 인증 상태
     */
    val codeState = MutableStateFlow(INIT)

    /**
     * 학생증 사진이 앱에 올라갔는지
     */
    val isUpload = MutableStateFlow(false)

    /**
     * 학생증 이미지 이름
     */
    var imgFileName = MutableStateFlow("")


    /************************************************
     * 입력을 받습니다
     ***************************************/

    /**
     * 학교 웹메일을 입력받습니다
     */
    fun mailInputText(cs : CharSequence) {
        cs.toString().let { text ->
            if(webMailInput.value != text) webMailInput.value = text
            if (emailValidation(text)) webMailState.value = INIT
        }
    }

    /**
     * 인증 코드를 입력받습니다
     */
    fun codeInputText(cs : CharSequence) {
        cs.toString().let {
            if(codeInput.value != it) codeInput.value = it
        }
    }

    /************************************************
     * 입력을 검사합니다
     ***************************************/

    /**
     * 이메일 형식을 검사합니다
     */
    private fun emailValidation(input : String) : Boolean {
        return !TextUtils.isEmpty(input) && input.contains("@") && input.contains(certifyModel.getUnivAddress())
    }


    /************************************************
     * 클릭 시 실행
     ***************************************/

    /**
     * 인증 방식을 누릅니다
     */
    fun changePage(){
        currentPage.value = !currentPage.value
    }

    /**
     * 학교 웹메일 X 버튼을 누릅니다
     */
    fun deleteMailInput() {
        webMailInput.value = ""
        webMailState.value = INIT
        certifyModel.flagSendSuccess.value = false
    }

    /**
     * 학교 웹메일 키보드 보내기 버튼을 누릅니다
     * 1. 이메일 정규식에 맞는지 확인합니다
     * 2. 서버에 해당 이메일로 인증 메일을 보내달라 요청합니다
     * 3. 인증 메일 전송에 성공했다면 웹메일 상태를 완료로 바꿔줍니다
     */
    fun onSendMailClick()
    {
        if (emailValidation(webMailInput.value))
        {

            requestSendMail()

            invokeBooleanFlow(
                certifyModel.flagSendSuccess,
                {
                    webMailState.value = INIT
                },
                {
                    webMailState.value = DONE
                }
            )

        }
        else webMailState.value = ERROR
    }

    /**
     * 인증 코드 X 버튼을 누릅니다
     */
    fun deleteCodeInput() {
        codeInput.value = ""
        codeState.value = INIT
    }

    /**
     * 인증 코드 키보드 완료 버튼을 누릅니다
     * 1. 서버에 인증 코드가 맞는지 확인 요청합니다
     * 2. 맞다면 인증 코드 상태를 완료로 변경합니다
     * 3. 틀리다면 인증 코드 상태를 에러로 변경합니다
     */
    fun onCheckCodeClick()
    {
        requestCheckCode()

        invokeBooleanFlow(
            certifyModel.flagCodeSuccess,
            {codeState.value = ERROR},
            {codeState.value = DONE}
        )
    }

    /**
     * 사진 업로드하기 버튼을 누릅니다
     */
    fun onUploadImageClick(){

        connect.loadImage {
            imgFileName.value = FileUtil.getRealPath(certifyModel.context,it).toString()
            certifyModel.setImgUri(imgFileName.value)
            isUpload.value = true
        }
    }

    /**
     * 업로드된 학생증 사진을 지웁니다
     */
    fun onDeleteImageClick()
    {
        imgFileName.value = ""
        isUpload.value = false
    }

    /**
     * 완료 버튼을 누릅니다 - 웹메일
     */
    fun onSubmit()
    {

        navigation.removeHistory(Page.PROFILE_WEBMAIL)
        navigation.removeHistory(Page.PROFILE_CERTIFICATION)
        navigation.revealHistory()
    }

    /**
     * 완료 버튼을 누릅니다 - 학생증 인증
     * 1. 학생증 인증을 요청합니다
     * 2. 사진 업로드가 성공하면 편집 화면으로 돌아갑니다
     */
    fun onSubmitStudentId()
    {
        requestCertifyStudentId()

        invokeBooleanFlow(
            certifyModel.flagUploadSuccess,
            {},
            (::onSubmit)
        )
    }

    /**
     * 다음에 하기 버튼을 누릅니다
     */
    fun onCancel()
    {
        navigation.revealHistory()
    }

    /************************************************
     * API를 요청합니다
     ***************************************/

    /**
     * 메일 인증을 요청합니다
     */
    private fun requestSendMail()
    {
        certifyModel.setAddress(webMailInput.value)
        certifyModel.sendMailCode()
    }

    /**
     * 인증 코드 확인을 요청합니다
     */
    private fun requestCheckCode()
    {
        certifyModel.setCode(codeInput.value)
        certifyModel.checkMailCode()
    }

    /**
     * 학생증 인증을 요청합니다
     */
    private fun requestCertifyStudentId()
    {
        if (isUpload.value)
        {
            certifyModel.certifySchoolId()
        }
    }


    /************************************************
     * 초기화 합니다
     ***************************************/

    /**
     * 상태를 초기화합니다
     */
/*    fun clearAllState(){
        webMailInput.value = ""
        codeInput.value = ""
        webMailState.value = INIT
        codeState.value = INIT
        isUpload.value = false
    }*/


    companion object {
        const val WEB_MAIL_PAGE = false
        const val SCHOOL_ID_PAGE = true

        const val INIT = 0
        const val ERROR = 1
        const val DONE = 2
    }


}