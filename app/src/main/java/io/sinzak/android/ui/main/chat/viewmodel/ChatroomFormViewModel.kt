package io.sinzak.android.ui.main.chat.viewmodel

import android.text.Editable
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.chat.ChatStorage
import io.sinzak.android.remote.dataclass.chat.ChatMsg
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.chat.ChatMsgAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChatroomFormViewModel @Inject constructor(
    private val storage: ChatStorage
): BaseViewModel() {

    private val msgList: MutableList<ChatMsg> = mutableListOf()

    val isProductExist get() = storage.chatProductExistFlag

    fun sendTypedMsg(text: Editable){

        storage.sendMsg(text.toString())

    }

    fun openImageUpload(){
        if (!isProductExist.value) return
        uiModel.loadImage {
            storage.postImage(it)
        }
    }
}