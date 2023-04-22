package io.sinzak.android.ui.main

import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.BuildConfig
import io.sinzak.android.constants.CODE_STORE_VERSION
import io.sinzak.android.di.NetStatus
import io.sinzak.android.enums.Page
import io.sinzak.android.model.chat.ChatStorage
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import io.sinzak.android.system.App.Companion.prefs

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

    fun showChatThroughAlarm(uuid: String?){
        navigation.changePage(Page.CHAT)
        if (uuid != null) {
            storage.loadExistChatroom(uuid)
            navigation.changePage(Page.CHAT_ROOM)
        }
    }

    fun checkAppUpdate(){
        /*val appVersion = BuildConfig.VERSION_CODE*/
        val appVersion = 11
        val storeVersion = prefs.getInt(CODE_STORE_VERSION, appVersion)
        if (storeVersion > appVersion) uiModel.updateAppDialog()
    }




}