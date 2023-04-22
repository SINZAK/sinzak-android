package io.sinzak.android.model.chat

import android.content.Context
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import io.sinzak.android.constants.*
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.chat.*
import io.sinzak.android.remote.dataclass.response.chat.*
import io.sinzak.android.remote.dataclass.response.market.MarketDetailResponse
import io.sinzak.android.remote.retrofit.CallImpl
import io.sinzak.android.system.LogDebug
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
        LogDebug(javaClass.name,"잡 클리어")
        job?.cancel()
        job = null
    }
    fun fetchRoomListJob(scope: CoroutineScope){
        forceClearJob()
        job = scope.launch {
            while(true){
                delay(10000)
                getChatRoomList()
            }
        }
    }

    fun fetchRoomListByPostJob(scope: CoroutineScope){
        forceClearJob()
        job = scope.launch {
            while(true){
                delay(10000)
                getChatRoomFromPost(postId, postType)
            }
        }
    }


    private fun clearChatMsg(){
        _chatMsg.value = mutableListOf()
    }

    private var currentChatRoom: ChatUtil? = null


    private var postId: Int = -1
    private var postType: String = "product"
    private var pendingMsg: String = ""

    val newChatRoomFlag = MutableStateFlow(false)
    val newChatNewMsgFlag = MutableStateFlow(false)


    /**
     * 채팅이 없는 상품에 채팅 생성
     */
    private fun makeChatroom(postDetail: MarketDetailResponse.Detail, type: String){
        LogDebug(javaClass.name, "새 채팅 생성")
        this.postId = postDetail.productId
        this.postType = type
        _chatRoomInfo.value =
            ChatRoomResponse.Data(
                roomName = postDetail.author,
                productId = postDetail.productId,
                productName = postDetail.title,
                thumbnail = postDetail.imgUrls?.let{
                   if(it.isNotEmpty()) it.last()
                   else ""
                }?:"",
                complete = postDetail.complete,
                suggest = postDetail.priceSuggestEnable,
                userId = postDetail.authorId,
                price = postDetail.price,
                postType = if (type == "product") "PRODUCT" else "WORK"
            )
    }

    /**
     * 채팅이 없는 채팅방에 메세지를 보낸걸 저장합니다
     * 채팅이 없는 채팅방에 메세지를 보낸걸 알립니다
     */
    fun getPendingMsg(msg: String)
    {
        pendingMsg = msg
        newChatNewMsgFlag.value = true
    }

    /**
     * 존재하고 있는 채팅방을 불러옵니다
     */
    fun loadExistChatroom(roomUuid : String){
        makeChatRoom(roomUuid)
        loadChatRoomDetailInfo(roomUuid)
        getChatroomMsg(roomUuid)
        currentChatroomUUID = roomUuid
    }

    /**
     * 1. 채팅방을 만듭니다
     */
    private fun makeChatRoom(roomId: String){
        currentChatRoom = ChatUtil(roomId,::onReceiveChatMsg, ::onFinishSend)
    }

    /**
     * 2.채팅방 정보를 불러옵니다
     */
    private fun loadChatRoomDetailInfo(roomId : String){

        _chatRoomInfo.value = null
        remote.sendRequestApi(
            CallImpl(
                API_GET_CHATROOM_DETAIL,
                this,
                paramStr0 = roomId
            )
        )
    }

    /**
     * 3. 채팅방 메세지를 불러옵니다
     */
    private fun getChatroomMsg(roomId : String){

        _chatMsg.value = mutableListOf()
        remote.sendRequestApi(
            CallImpl(
                API_GET_CHATROOM_MSG,
                this,
                paramStr0 = roomId
            )
        )
    }

    /**
     * 작품 상세에서 채팅방에 접근합니다
     */
    fun accessChatRoomFromProduct(postId: Int, postType: String, art : MarketDetailResponse.Detail) {
        clearChatMsg()
        makeChatroom(art,postType)
        checkRoomExist(postId,postType)
        chatProductExistFlag.value = true
    }

    /*****************************************************
     ** API를 요청합니다
     ******************************************************/

    /**
     * 갖고 있는 모든 채팅방을 요청합니다
     */
    fun getChatRoomList(){
        _chatRooms.value = mutableListOf()
        CallImpl(API_GET_CHATROOM_LIST,this).apply{
            remote.sendRequestApi(this)
        }
    }

    /**
     * 새로운 채팅방 만들기를 요청합니다
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

    /**
     * 상품에 딸려있는 채팅방 리스트를 요청합니다
     */
    fun getChatRoomFromPost(postId: Int, postType: String){
        this.postId = postId
        this.postType = postType
        _chatRooms.value = mutableListOf()
        CallImpl(API_GET_CHATROOM_POST_LIST, this, paramInt0 = postId, paramStr0 = postType).apply{
            remote.sendRequestApi(this)
        }
    }

    /**
     * 채팅방에 이미지 업로드
     */
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

    /**
     * 채팅방에 메세지를 보냅니다
     */
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
        }

    }

    /**
     * 채팅방이 존재하는지 확인합니다
     */
    private fun checkRoomExist(postId: Int, postType: String) {
        CallImpl(
            API_CHECK_CHATROOM_EXIST,
            this,
            paramInt0 = postId,
            paramStr0 = postType
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    private fun onCheckRoomResponse(response : ChatRoomCheckResponse) {
        response.data?.let {
            if (it.exist) {
                newChatRoomFlag.value = false
                loadExistChatroom(it.uuid!!)
            }
            else newChatRoomFlag.value = true
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

    /**
     * 채팅방을 나갑니다
     */
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
                    _chatRooms.value = body.data.toMutableList().asReversed()
                }

            }

            API_GET_CHATROOM_POST_LIST ->{
                if(body.success == true){
                    body as ChatRoomListResponse
                    _chatRooms.value = body.data.toMutableList().asReversed()
                }
            }

            API_CREATE_CHATROOM ->{
                if(body.success == true){
                    body as ChatCreateResponse
                    body.data?.let {
                        makeChatRoom(it.roomUuid!!)
                        sendMsg(pendingMsg,ChatUtil.TYPE_TEXT)
                        pendingMsg = ""

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

            API_CHECK_CHATROOM_EXIST -> {
                onCheckRoomResponse(body as ChatRoomCheckResponse)
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {
        when(api) {
            API_CHECK_CHATROOM_EXIST -> {
                newChatRoomFlag.value = true
            }
        }
    }



}