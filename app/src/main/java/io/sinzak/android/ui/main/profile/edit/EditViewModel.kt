package io.sinzak.android.ui.main.profile.edit

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.profile.ProfileEditModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(val model: ProfileEditModel, val pModel : ProfileModel) : BaseViewModel() {

    /**
     * 조회중인 프로필
     */
    val profile get() = pModel.profile

    /********************************
     * INPUT
     ********************************/
    private val _introduction = MutableStateFlow("")
    val introduction : StateFlow<String> get() = _introduction

    fun typeInputText(cs : CharSequence) {
        cs.toString().let {
            if(_introduction.value != it){
                _introduction.value = it
                model.setIntroduction(it)
            }
        }
    }

    fun inputName(cs: CharSequence)
    {
        model.setName(cs.toString())
    }

    /********************************
     * REQUEST
     ********************************/
    /**
     * 완료 버튼 클릭시 동작
     */
    fun onSubmit()
    {
        model.updateProfile()
    }

    /**
     * 학교 인증 페이지 이동
     */
    fun gotoCertificationPage(hasCerti : Boolean){
        if(!hasCerti) navigation.changePage(Page.PROFILE_CERTIFICATION)
        else return
    }

    /**
     * 인증 작가 페이지 이동
     */
    fun gotoVerifyPage(){
        navigation.changePage(Page.PROFILE_VERIFY)
    }

}