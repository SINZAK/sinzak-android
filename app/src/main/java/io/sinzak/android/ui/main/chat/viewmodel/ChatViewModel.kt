package io.sinzak.android.ui.main.chat.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.enums.Page
import io.sinzak.android.model.chat.ChatStorage
import io.sinzak.android.remote.dataclass.chat.ChatRoom
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.chat.ChatRoomAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val storage: ChatStorage
) : BaseViewModel() {
    val chatRoomAdapter = ChatRoomAdapter{
        openChatRoom(it)
    }

    private fun openChatRoom(chat:ChatRoom){
        storage.loadExistChatroom(chat.roomUuid.toString())
        navigation.changePage(Page.CHAT_ROOM)
    }


    private val chatRoomCollector =
        storage.chatRooms.onEach {
            chatRoomAdapter.chatList = it
            chatRoomAdapter.notifyDataSetChanged()
        }.launchIn(viewModelScope)

    fun getRemoteChatRoomList()
    {
        storage.getChatRoomList()
    }

    fun fetchRoomListJob()
    {
        storage.fetchRoomListJob(viewModelScope)
    }

    fun clearJob()
    {
        storage.forceClearJob()
    }


    init {
        storage.chatProductExistFlag.value = false
        useFlag(storage.chatRoomLeaveFlag){
            chatRoomAdapter.removeByUUID(storage.deleteRoomUUID.value)
            storage.deleteRoomUUID.value = ""
        }
    }

    fun onBackPressed()
    {
        navigation.revealHistory()
    }

}