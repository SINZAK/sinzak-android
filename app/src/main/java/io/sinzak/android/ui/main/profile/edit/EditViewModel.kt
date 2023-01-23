package io.sinzak.android.ui.main.profile.edit

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.profile.ProfileEditModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(val model: ProfileEditModel, val pModel : ProfileModel) : BaseViewModel() {

    private val _hasCertification = MutableStateFlow(false)
    val hasCertification : StateFlow<Boolean> get() = _hasCertification

    private val _introduction = MutableStateFlow("")
    val introduction : StateFlow<String> get() = _introduction

    val profile get() = pModel.profile

    fun typeInputText(cs : CharSequence) {
        cs.toString().let {
            if(_introduction.value != it){
                _introduction.value = it
            }
        }
    }

    fun inputName(cs: CharSequence)
    {
        model.setName(cs.toString())
    }

    fun onSubmit()
    {
        model.setIntroduction(_introduction.value)
        model.updateProfile()
    }

}