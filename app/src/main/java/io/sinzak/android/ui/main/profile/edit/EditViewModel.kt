package io.sinzak.android.ui.main.profile.edit

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor() : BaseViewModel() {

    private val _hasCertification = MutableStateFlow(false)
    val hasCertification : StateFlow<Boolean> get() = _hasCertification

}