package io.sinzak.android.ui.main.outsourcing.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class OutsourcingViewModel @Inject constructor() : BaseViewModel() {

    val isClientList = MutableStateFlow(true)
}