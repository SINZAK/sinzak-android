package io.sinzak.android.ui.main

import io.sinzak.android.enums.Page
import io.sinzak.android.model.navigate.Navigation
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MainBottomViewModel @Inject constructor(val navigation: Navigation): BaseViewModel() {

    private val _currentButton = MutableStateFlow(0)
    val currentButton : StateFlow<Int> get() = _currentButton


    fun setCurrentButton(button : Int){

        when(button)
        {
            0 -> navigation.changePage(Page.HOME)
        }
        _currentButton.value = button

    }

}