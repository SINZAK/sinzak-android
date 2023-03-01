package io.sinzak.android.ui.main.chat.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.model.chat.ChatStorage
import io.sinzak.android.remote.dataclass.chat.ChatMsg
import io.sinzak.android.system.LogInfo
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.chat.ChatConnect
import io.sinzak.android.ui.main.chat.ChatMsgAdapter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChatroomViewModel @Inject constructor(
    private val storage: ChatStorage,
    private val connect: ChatConnect
): BaseViewModel() {
    private val chatMsgList = mutableListOf<ChatMsg>()

    val chatAdapter = ChatMsgAdapter(chatMsgList)

    val chatRoom = storage.chatRoomInfo

    private val _roomName = MutableStateFlow("")
    val roomName: StateFlow<String> = _roomName

    fun onBackPressed(){
        uiModel.navigation.revealHistory()
    }

    fun invokeAdapter(rv: RecyclerView): ChatMsgAdapter{
        chatAdapter.rv = rv
        return chatAdapter
    }

    private val chatroomInfoCollect = storage.chatRoomInfo.onEach {
        it?.let{chatroom->
            _roomName.value = chatroom.roomName
        }
    }.launchIn(viewModelScope)

    private val chatCollectJob = storage.chatMsg.onEach{
        chatUpdater(it)
    }.launchIn(viewModelScope)

    private val singleChatCollect = storage.chatMsgFlow.onEach {
        it?.let{
            LogInfo(javaClass.name,"CHAT COLLECT: $it")
            chatMsgList.add(it)
            chatAdapter.notifyMsgAdded(1)
        }
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
        val newMsg = chat.filter { it !in chatMsgList }

        chatMsgList.addAll(newMsg)
        chatAdapter.notifyMsgAdded(newMsg.size)




    }


    fun openChatDialog(){
        connect.showChatDialog(
            ::blockUser,
            ::reportUser,
            ::leaveChatroom
        )
    }


    private fun blockUser(){

    }

    private fun reportUser(){


    }

    private fun leaveChatroom(){
        storage.leaveChatroom()
        uiModel.navigation.revealHistory()
    }

}