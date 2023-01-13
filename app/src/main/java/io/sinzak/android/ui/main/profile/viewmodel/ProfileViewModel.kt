package io.sinzak.android.ui.main.profile.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(val profileModel: ProfileModel) : BaseViewModel() {

    private val _isVerify = MutableStateFlow(false)
    val isVerify : StateFlow<Boolean> get() = _isVerify

    val profile get() = profileModel.profile

    private val _isMyProfile = MutableStateFlow(false)
    val isMyProfile : StateFlow<Boolean> get() = _isMyProfile

    //ui를 보기위한 임시 함수 (지워야됨)
    fun changeStatus(status : Boolean) {
        _isMyProfile.value = status
    }

}