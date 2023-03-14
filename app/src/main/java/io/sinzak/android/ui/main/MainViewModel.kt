package io.sinzak.android.ui.main

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.di.NetStatus
import io.sinzak.android.enums.Page
import io.sinzak.android.model.chat.ChatStorage
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val netStatus: NetStatus,
    private val storage: ChatStorage
) : BaseViewModel() {

    private val _showBottomMenu = MutableStateFlow(false)
    val showBottomMenu : StateFlow<Boolean> get() = _showBottomMenu



    fun setBottomMenuVisibility(boolean: Boolean)
    {
        _showBottomMenu.value = boolean
    }

    fun showChatThroughAlarm(uuid: String){
        storage.loadExistChatroom(uuid)
        navigation.changePage(Page.CHAT)
        navigation.changePage(Page.CHAT_ROOM)
    }




}