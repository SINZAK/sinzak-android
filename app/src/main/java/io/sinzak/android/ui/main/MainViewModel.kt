package io.sinzak.android.ui.main

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

    private val _showBottomMenu = MutableStateFlow(false)
    val showBottomMenu : StateFlow<Boolean> get() = _showBottomMenu



    fun setBottomMenuVisibility(boolean: Boolean)
    {
        _showBottomMenu.value = boolean
    }
}