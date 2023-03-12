package io.sinzak.android.model.chat

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import io.sinzak.android.constants.*
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.chat.*
import io.sinzak.android.remote.dataclass.response.market.MarketDetailResponse
import io.sinzak.android.remote.retrofit.CallImpl
import io.sinzak.android.system.App.Companion.prefs
import io.sinzak.android.system.LogDebug
import io.sinzak.android.system.LogInfo
import io.sinzak.android.utils.ChatUtil
import io.sinzak.android.utils.FileUtil
import io.sinzak.android.utils.TimeUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatStorage @Inject constructor(@ApplicationContext val context: Context) : BaseModel() {


    private val _chatRooms = MutableStateFlow(mutableListOf<ChatRoom>())
    val chatRooms : StateFlow<List<ChatRoom>> get() = _chatRooms

    private val _chatMsg = MutableStateFlow(mutableListOf<ChatMsg>())
    val chatMsg : StateFlow<MutableList<ChatMsg>> get() = _chatMsg

    private val _chatRoomInfo = MutableStateFlow<ChatRoomResponse.Data?>(null)
    val chatRoomInfo: StateFlow<ChatRoomResponse.Data?> = _chatRoomInfo

    val chatMsgFlow = MutableStateFlow<ChatMsg?>(null)

    private var currentChatroomUUID = ""

    private var job: Job? = null

    val chatProductExistFlag = MutableStateFlow(false)

    val chatRoomLeaveFlag = MutableStateFlow(false)
    val deleteRoomUUID = MutableStateFlow("")


    fun forceClearJob(){
        job?.cancel()
        job = null
    }
    fun fetchRoomListJob(scope: CoroutineScope){
        forceClearJob()
        job = scope.launch {
            while(true){
                getChatRoomList()
                delay(10000)
            }
        }
    }

    fun fetchRoomListByPostJob(scope: CoroutineScope, postId: Int, postType: String){
        forceClearJob()
        job = scope.launch {
            while(true){
                getChatRoomFromPost(postId, postType)
                delay(10000)
            }
        }
    }


    fun getChatRoomList(){
        _chatRooms.value = mutableListOf()
        CallImpl(API_GET_CHATROOM_LIST,this).apply{
            remote.sendRequestApi(this)
        }
    }

    fun getChatRoomFromPost(postId: Int, postType: String){
        CallImpl(API_GET_CHATROOM_POST_LIST, this, paramInt0 = postId, paramStr0 = postType).apply{
            remote.sendRequestApi(this)
        }
    }

    fun clearChatMsg(){
        _chatMsg.value = mutableListOf()
    }


    lateinit var product: MarketDetailResponse.Detail



    private var currentChatRoom: ChatUtil? = null


    private var postId: Int = -1
    private var postType: String = "product"
    private var pendingMsg: String = ""

    /**
     * 채팅이 없는 상품에 채팅 생성
     */
    fun makeChatroom(postDetail: MarketDetailResponse.Detail, type: String){
        this.postId = postDetail.productId
        this.postType = type
        _chatRoomInfo.value =
            ChatRoomResponse.Data(
                roomName = postDetail.author,
                productId = postDetail.productId,
                productName = postDetail.title,
                thumbnail = postDetail.imgUrls?.let{
                   if(it.isNotEmpty()) it[0]
                   else ""
                }?:"",
                complete = postDetail.complete,
                suggest = postDetail.priceSuggestEnable,
                userId = postDetail.authorId,
                price = postDetail.price,
                postType = if (type == "product") "PRODUCT" else "WORK"
            )
    }

    fun loadExistChatroom(chatroom: ChatRoom){
        makeChatRoom(chatroom.roomUuid.toString())
        loadChatRoomDetailInfo(chatroom)
        getChatroomMsg(chatroom)
        currentChatroomUUID = chatroom.roomUuid.toString()
    }

    fun getChatroomMsg(chatroom: ChatRoom){

        _chatMsg.value = mutableListOf()
        remote.sendRequestApi(
            CallImpl(
                API_GET_CHATROOM_MSG,
                this,
                paramStr0 = chatroom.roomUuid.toString()
            )
        )
    }

    fun loadChatRoomDetailInfo(chatroom: ChatRoom){

        _chatRoomInfo.value = null
        remote.sendRequestApi(
            CallImpl(
                API_GET_CHATROOM_DETAIL,
                this,
                paramStr0 = chatroom.roomUuid.toString()
            )
        )
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
            sendMsg(pendingMsg,ChatUtil.TYPE_TEXT)
            pendingMsg = ""
        }
    }


    fun postImage(imgUrls: List<Uri>){
        remote.sendRequestApi(
            CallImpl(
                API_CHAT_UPLOAD_IMG,
                this,
                multipartList = imgUrls.map{uri->
                    FileUtil.getBitmapFile(context, uri)
                }.map{
                    FileUtil.getMultipart(context,"multipartFile", it)
                },
                paramStr0 = currentChatroomUUID
            )
        )


    }


    fun sendMsg(msg: String , type : String){
        if(msg.isEmpty())
            return

        addChatMsgOnTail(null)

        currentChatRoom?.let{

            it.sendMsg(msg, type = type)
            addChatMsgOnTail(
                ChatMsg(
                    msgId = msg.hashCode(),
                    msg = msg,
                    isMyChat = true,
                    sendTime = TimeUtil.getCurrentDateTimeString(),
                    type = type
                )
            )
        }?:run{
            LogInfo(javaClass.name,"CHATROOM EMPTY, TRY MAKE CHATROOM")
            pendingMsg = msg
            makeChatroom()
        }

    }

    private fun onReceiveChatMsg(msg: ChatMsg){
        addChatMsgOnTail(null)
        CoroutineScope(Dispatchers.IO).launch {
            msg.isMyChat = false
            addChatMsgOnTail(msg)
        }



    }

    private fun addChatMsgOnTail(msg: ChatMsg?){
        chatMsgFlow.value = msg


    }

    private fun onFinishSend(success:Boolean){

    }

    fun leaveChatroom(){
        LogDebug(javaClass.name, "채팅방 나가기 실행 : $currentChatroomUUID 방에서 나감")
        deleteRoomUUID.value = currentChatroomUUID
        currentChatRoom?.destroyChatroom()
        currentChatRoom = null
        chatRoomLeaveFlag.value = true
    }


    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api){
            API_GET_CHATROOM_LIST ->{
                if(body.success == true){
                    body as ChatRoomListResponse
                    _chatRooms.value = body.data.toMutableList()
                }

            }

            API_GET_CHATROOM_POST_LIST ->{
                if(body.success == true){
                    body as ChatRoomListResponse
                    _chatRooms.value = body.data.toMutableList()
                }
            }

            API_CREATE_CHATROOM ->{
                if(body.success == true){
                    body as ChatCreateResponse
                    body.data?.let{chatroom->
                        makeChatRoom(chatroom.roomUuid!!)
                        loadExistChatroom(chatroom)
                    }

                }
            }

            API_GET_CHATROOM_MSG ->{
                body as ChatRoomMsgResponse
                body.msgContent?.let{
                    _chatMsg.value = it.onEach{msg->
                        msg.isMyChat = msg.senderId == ChatUtil.senderId

                    }.toMutableList().asReversed()
                }
            }

            API_GET_CHATROOM_DETAIL ->{
                body as ChatRoomResponse
                chatProductExistFlag.value = body.success!!
                body.data?.let { chatroom->
                    _chatRoomInfo.value = chatroom
                }

            }

            API_CHAT_UPLOAD_IMG ->{
                body as ChatImageUploadResponse
                body.data?.forEach { url ->
                    sendMsg(url.imageUrl, ChatUtil.TYPE_IMAGE)
                    LogDebug(javaClass.name , "사진 전송 성공")
                }
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }



}