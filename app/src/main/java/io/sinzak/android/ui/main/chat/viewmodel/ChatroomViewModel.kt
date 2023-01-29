package io.sinzak.android.ui.main.chat.viewmodel

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
class ChatroomViewModel @Inject constructor(
    private val storage: ChatStorage
): BaseViewModel() {
    private val chatMsgList = mutableListOf<ChatMsg>()

    val chatAdapter = ChatMsgAdapter(chatMsgList)

    private val chatCollectJob = storage.chatMsg.onEach{
        chatUpdater(it)
    }.launchIn(viewModelScope)

    private fun chatUpdater(chat: MutableList<ChatMsg>){

        //아얘 다른 내용 -> List 초기화

        //페이징으로 불러온 내용 -> 리스트 상단에 추가

        //추후 입력으로 불러온 내용 -> 리스트 하단에 추가


        if(chat.filter{it in chatMsgList}.isEmpty()){
            chatMsgList.clear()
            chatMsgList.addAll(chat)
            chatAdapter.notifyNewChatRoom()
            return
        }

    }


}