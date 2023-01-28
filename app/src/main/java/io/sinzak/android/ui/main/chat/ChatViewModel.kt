package io.sinzak.android.ui.main.chat

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.chat.ChatStorage
import io.sinzak.android.ui.base.BaseViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val storage: ChatStorage
) : BaseViewModel() {

    fun getChatRooms(){
        storage.getChatRoomList()
    }

    val chatRoomAdapter = ChatRoomAdapter()

    private val chatRoomRequest = viewModelScope.launch {
        while(true){
            getChatRooms()
            delay(10000)
        }


    }



    private val chatRoomCollector =
        storage.chatRooms.onEach {
            chatRoomAdapter.chatList = it
        }.launchIn(viewModelScope)


    override fun onCleared() {
        super.onCleared()
        chatRoomRequest.cancel()
        chatRoomCollector.cancel()
    }


}