package io.sinzak.android.model.chat

import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.sinzak.android.constants.API_GET_CHATROOM_LIST
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.chat.ChatMsg
import io.sinzak.android.remote.dataclass.chat.ChatRoom
import io.sinzak.android.remote.dataclass.chat.ChatRoomListResponse
import io.sinzak.android.remote.retrofit.CallImpl
import io.sinzak.android.utils.ChatUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@ActivityRetainedScoped
class ChatStorage @Inject constructor() : BaseModel() {


    private val _chatRooms = MutableStateFlow(mutableListOf<ChatRoom>())
    val chatRooms : StateFlow<List<ChatRoom>> get() = _chatRooms

    private val _chatMsg = MutableStateFlow(mutableListOf<ChatMsg>())
    val chatMsg : StateFlow<MutableList<ChatMsg>> get() = _chatMsg

    fun getChatRoomList(){
        CallImpl(API_GET_CHATROOM_LIST,this).apply{
            remote.sendRequestApi(this)
        }
    }





    private var currentChatRoom: ChatUtil? = null


    fun makeChatRoom(roomId: String){
        currentChatRoom = ChatUtil(roomId,::onReceiveChatMsg, ::onFinishSend)
    }


    fun sendMsg(msg: String){
        currentChatRoom?.sendMsg(msg)
        addChatMsgOnTail(
            ChatMsg(
                msgId = msg.hashCode(),
                msg = msg,
                isMyChat = true
            )
        )
    }

    private fun onReceiveChatMsg(msg: ChatMsg){
        msg.isMyChat = false
        addChatMsgOnTail(msg)


    }

    private fun addChatMsgOnTail(msg: ChatMsg){
        _chatMsg.value = chatMsg.value.apply{
            add(msg)
        }
    }

    private fun onFinishSend(success:Boolean){

    }

    fun destroyChatroom(){
        currentChatRoom?.destroySession()
        currentChatRoom = null
    }


    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api){
            API_GET_CHATROOM_LIST ->{
                if(body.success == true){
                    body as ChatRoomListResponse
                    _chatRooms.value = body.data.toMutableList()
                }

            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }
}