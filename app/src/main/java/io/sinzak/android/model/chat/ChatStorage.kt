package io.sinzak.android.model.chat

import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.sinzak.android.constants.API_GET_CHATROOM_LIST
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.chat.ChatRoom
import io.sinzak.android.remote.dataclass.chat.ChatRoomListResponse
import io.sinzak.android.remote.retrofit.CallImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@ActivityRetainedScoped
class ChatStorage @Inject constructor() : BaseModel() {


    private val _chatRooms = MutableStateFlow(mutableListOf<ChatRoom>())
    val chatRooms : StateFlow<List<ChatRoom>> get() = _chatRooms

    fun getChatRoomList(){
        CallImpl(API_GET_CHATROOM_LIST,this).apply{
            remote.sendRequestApi(this)
        }
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