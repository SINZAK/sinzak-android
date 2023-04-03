package io.sinzak.android.ui.main.chat.viewmodel

import android.text.Editable
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.chat.ChatStorage
import io.sinzak.android.system.LogDebug
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.utils.ChatUtil
import javax.inject.Inject

@HiltViewModel
class ChatroomFormViewModel @Inject constructor(
    private val storage: ChatStorage
): BaseViewModel() {

    val isProductExist get() = storage.chatProductExistFlag

    fun sendTypedMsg(text: Editable){

        if (storage.newChatRoomFlag.value){
            storage.getPendingMsg(text.toString())
            storage.newChatRoomFlag.value = false
            return
        }
        LogDebug(javaClass.name, "메세지 보냄")
        storage.sendMsg(text.toString(), ChatUtil.TYPE_TEXT)

    }

    fun openImageUpload(){
        if (!isProductExist.value) return
        uiModel.loadImage {
            storage.postImage(it)
        }
    }
}