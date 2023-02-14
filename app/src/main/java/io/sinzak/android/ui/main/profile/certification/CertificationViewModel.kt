package io.sinzak.android.ui.main.profile.certification

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.certify.CertifyModel
import io.sinzak.android.model.insets.SoftKeyModel
import io.sinzak.android.remote.dataclass.local.SchoolData
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.profile.certification.adapter.CertificationAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
open class CertificationViewModel @Inject constructor(
    private val certifyModel: CertifyModel,
    val soft: SoftKeyModel,
) : BaseViewModel() {

    /**
     * 입력된 학교를 저장
     */
    val schoolInput = MutableStateFlow("")

    /**
     * 학교 리스트를 보여줄지?
     */
    val showSchoolList = MutableStateFlow(false)

    /**
     * 학교 데이터를 저장하는 공간
     */
    var school : SchoolData? = null

    /**
     * 학교 데이터가 삽입되었는지? => 버튼 활성화 제어
     */
    val schoolInserted = MutableStateFlow(false)

    /**
     * 회원가입중인지?
     */
    open val isPartOfSignup = false

    /**
     * 학교 리스트 어댑터
     */
    val certificationAdapter = CertificationAdapter(::onSchoolItemClick)


    /************************************************
     * 입력을 받습니다
     ***************************************/

    /**
     * 학교를 입력받습니다
     */
    fun schoolInputText(cs: CharSequence) {
        cs.toString().let { text ->
            setSchoolInput(text)
            filterSchool(text)
        }
    }

    /**
     * 받은 입력에 따라 상태를 제어합니다
     */
    private fun setSchoolInput(text : String)
    {
        if (schoolInput.value != text) schoolInput.value = text
        if (text == "") schoolInserted.value = false
    }

    /**
     * 학교 리스트를 필터링합니다
     */
    private fun filterSchool(text : String)
    {
        certificationAdapter.setSchoolList(
            list = signModel.univList.filter { it.schoolName.contains(text) }.toList(),
            input = text
        )
    }


    /************************************************
     * 클릭 시 실행
     ***************************************/

    /**
     * 입력 공간을 누릅니다
     */
    open fun openSchoolList()
    {
        showSchoolList.value = true
    }

    /**
     * 학교 리스트 중 하나를 누릅니다
     */
    private fun onSchoolItemClick(item : SchoolData)
    {
        school = item
        schoolInput.value = item.schoolName
        schoolInserted.value = true
        showSchoolList.value = false
        soft.hideKeyboard()
    }

    /**
     * 다음 버튼을 누릅니다
     */
    open fun onSubmit() {
        certifyModel.setUniv(school!!)
        navigation.changePage(Page.PROFILE_WEBMAIL)
    }

    /**
     * 다음에 하기 버튼을 누릅니다
     */
    open fun onCancel(){
        navigation.revealHistory()
    }

    /************************************************
     * 초기화 합니다
     ***************************************/
/*    fun clearAllState()
    {
        schoolInput.value = ""
        showSchoolList.value = false
        schoolInserted.value = false
        school = null
        certifyModel.clearUniv()
    }*/
}