package io.sinzak.android.ui.main

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainBottomViewModel @Inject constructor(): BaseViewModel() {

    private val _currentButton = MutableStateFlow(0)
    val currentButton : StateFlow<Int> get() = _currentButton

    init{
        val pages = listOf(
            Page.HOME,
            Page.MARKET,
            Page.OUTSOURCING,
            Page.CHAT,
            Page.PROFILE
        )
        CoroutineScope(Dispatchers.Main).launch {

            invokeStateFlow(navigation.topPage){
                if(it in pages){
                    if(currentButton.value != pages.indexOf(it)){
                        _currentButton.value = pages.indexOf(it)
                    }
                }
            }
        }

    }


    fun setCurrentButton(button : Int){

        if(button == PAGE_PROFILE){
            if(!signModel.isLogin())
            {
                uiModel.gotoLogin()
                return
            }
        }

        when(button)
        {
            PAGE_HOME -> navigation.changePage(Page.HOME)
            PAGE_MARKET -> navigation.changePage(Page.MARKET)
            PAGE_OUTSOURCING -> navigation.changePage(Page.OUTSOURCING)
            PAGE_CHATTING -> navigation.changePage(Page.CHAT)
            PAGE_PROFILE -> navigation.changePage(Page.PROFILE)
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