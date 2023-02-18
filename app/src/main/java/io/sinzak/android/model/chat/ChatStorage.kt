package io.sinzak.android.model.chat

import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.sinzak.android.constants.API_CREATE_CHATROOM
import io.sinzak.android.constants.API_GET_CHATROOM_LIST
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.chat.ChatCreateResponse
import io.sinzak.android.remote.dataclass.chat.ChatMsg
import io.sinzak.android.remote.dataclass.chat.ChatRoom
import io.sinzak.android.remote.dataclass.chat.ChatRoomListResponse
import io.sinzak.android.remote.dataclass.response.market.MarketDetailResponse
import io.sinzak.android.remote.retrofit.CallImpl
import io.sinzak.android.system.LogDebug
import io.sinzak.android.system.LogInfo
import io.sinzak.android.utils.ChatUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatStorage @Inject constructor() : BaseModel() {


    private val _chatRooms = MutableStateFlow(mutableListOf<ChatRoom>())
    val chatRooms : StateFlow<List<ChatRoom>> get() = _chatRooms

    private val _chatMsg = MutableStateFlow(mutableListOf<ChatMsg>())
    val chatMsg : StateFlow<MutableList<ChatMsg>> get() = _chatMsg

    val chatMsgFlow = MutableStateFlow<ChatMsg?>(null)

    fun getChatRoomList(){
        CallImpl(API_GET_CHATROOM_LIST,this).apply{
            remote.sendRequestApi(this)
        }
    }


    lateinit var product: MarketDetailResponse.Detail



    private var currentChatRoom: ChatUtil? = null


    private var postId: Int = -1
    private var postType: String = "product"
    private var pendingMsg: String = ""

    /**
     * 채팅이 없는 상품에 채팅 생성
     */
    fun makeChatroom(postId: Int, postType: String){
        this.postId = postId
        this.postType = postType
    }

    /**
     * Request New Chatroom
     */
    fun makeChatroom(){
        CallImpl(
            API_CREATE_CHATROOM,
            this,
            paramInt0 = postId,
            paramStr0 = postType
        ).apply{
            remote.sendRequestApi(this)
        }
    }


    fun makeChatRoom(roomId: String){
        currentChatRoom = ChatUtil(roomId,::onReceiveChatMsg, ::onFinishSend)
        if(pendingMsg.isNotEmpty()){
            sendMsg(pendingMsg)
            pendingMsg = ""
        }
    }



    fun sendMsg(msg: String){
        if(msg.isEmpty())
            return
        currentChatRoom?.let{

            it.sendMsg(msg)
            addChatMsgOnTail(
                ChatMsg(
                    msgId = msg.hashCode(),
                    msg = msg,
                    isMyChat = true
                )
            )
        }?:run{
            LogInfo(javaClass.name,"CHATROOM EMPTY, TRY MAKE CHATROOM")
            pendingMsg = msg
            makeChatroom()
        }

    }

    private fun onReceiveChatMsg(msg: ChatMsg){
        msg.isMyChat = false
        addChatMsgOnTail(msg)


    }

    private fun addChatMsgOnTail(msg: ChatMsg){
        chatMsgFlow.value = msg


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

            API_CREATE_CHATROOM ->{
                if(body.success == true){
                    body as ChatCreateResponse
                    makeChatRoom(body.data?.roomUuid!!)
                }
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }
}