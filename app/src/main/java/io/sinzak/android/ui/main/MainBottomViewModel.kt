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
            PAGE_HOME -> navigation.changePage(Page.HOME)
            PAGE_MARKET -> navigation.changePage(Page.HOME)
            PAGE_OUTSOURCING -> navigation.changePage(Page.HOME)
            PAGE_CHATTING -> navigation.changePage(Page.HOME)
            PAGE_PROFILE -> navigation.changePage(Page.HOME)
        }
        _currentButton.value = button

    }

    companion object
    {
        const val PAGE_HOME = 0
        const val PAGE_MARKET = 1
        const val PAGE_OUTSOURCING = 2
        const val PAGE_CHATTING = 3
        const val PAGE_PROFILE = 4
    }

}