package io.sinzak.android.ui.main.chat.viewmodel

import android.os.Bundle
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.lifecycle.HiltViewModel
import io.sinzak.android.constants.CODE_USER_ID
import io.sinzak.android.constants.CODE_USER_REPORT_ID
import io.sinzak.android.constants.CODE_USER_REPORT_NAME
import io.sinzak.android.system.App.Companion.prefs
import io.sinzak.android.enums.Page
import io.sinzak.android.model.chat.ChatStorage
import io.sinzak.android.model.profile.UserCommandModel
import io.sinzak.android.remote.dataclass.chat.ChatMsg
import io.sinzak.android.system.LogDebug
import io.sinzak.android.system.LogInfo
import io.sinzak.android.ui.base.BaseViewModel
import io.sinzak.android.ui.main.chat.ChatConnect
import io.sinzak.android.ui.main.chat.ChatMsgAdapter
import io.sinzak.android.ui.main.profile.ProfileConnect
import io.sinzak.android.ui.main.profile.report.ReportSendViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChatroomViewModel @Inject constructor(
    private val storage: ChatStorage,
    private val connect: ChatConnect,
    private val commandModel: UserCommandModel
) : BaseViewModel() {
    private val chatMsgList = mutableListOf<ChatMsg>()

    val chatAdapter = ChatMsgAdapter(chatMsgList)

    val chatRoom = storage.chatRoomInfo

    private val _roomName = MutableStateFlow("")
    val roomName: StateFlow<String> = _roomName

    val myId = prefs.getString(CODE_USER_ID,"-1")

    val isProductExist get() = storage.chatProductExistFlag

    fun onBackPressed() {
        uiModel.navigation.revealHistory()
    }

    fun invokeAdapter(rv: RecyclerView): ChatMsgAdapter {
        chatAdapter.rv = rv
        return chatAdapter
    }

    private val chatroomInfoCollect = storage.chatRoomInfo.onEach {
        it?.let { chatroom ->
            _roomName.value = chatroom.roomName
        }
    }.launchIn(viewModelScope)


    private val chatCollectJob = storage.chatMsg.onEach {
        chatUpdater(it)
    }.launchIn(viewModelScope)

    private val singleChatCollect = storage.chatMsgFlow.onEach { newMessageOptional ->
        newMessageOptional?.let { newMessage ->
            LogInfo(javaClass.name, "CHAT COLLECT: $newMessage")
            chatMsgList.add(newMessage)
            chatAdapter.notifyMsgAdded(1)
        }
    }.launchIn(viewModelScope)

    private fun chatUpdater(chat: MutableList<ChatMsg>) {

        //아얘 다른 내용 -> List 초기화

        //페이징으로 불러온 내용 -> 리스트 상단에 추가

        //추후 입력으로 불러온 내용 -> 리스트 하단에 추가

        chat.filter { it !in chatMsgList }.let { pendingChat ->
            if (pendingChat.isEmpty()) {
                chatMsgList.clear()
                chatMsgList.addAll(chat)
                chatAdapter.notifyNewChatRoom()
                return
            }

            chatMsgList.addAll(pendingChat)
            chatAdapter.notifyMsgAdded(pendingChat.size)
        }
    }


    fun openSaleDialog() {
        connect.showOnSaleDialog {
            TODO()
        }
    }

    fun openChatDialog() {
        connect.showChatDialog(
            ::blockUser,
            ::reportUser,
            ::leaveChatroom
        )
    }


    private fun blockUser() {
        connect.userBlockDialog {
           TODO()
        }
    }

    private fun reportUser() {
        chatRoom.value?.let { info ->
            Bundle().apply {
                putString(CODE_USER_REPORT_NAME, info.roomName)
                putString(CODE_USER_REPORT_ID, info.userId)
                navigation.putBundleData(ReportSendViewModel::class, this)
            }
            navigation.changePage(Page.PROFILE_REPORT_TYPE)
        }


    }

    private fun leaveChatroom() {
        storage.leaveChatroom()
        uiModel.navigation.revealHistory()
    }

}