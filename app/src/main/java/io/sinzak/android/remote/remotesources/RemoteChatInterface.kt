package io.sinzak.android.remote.remotesources

import com.google.gson.JsonObject
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.chat.ChatCreateResponse
import io.sinzak.android.remote.dataclass.chat.ChatRoomListResponse
import io.sinzak.android.remote.dataclass.chat.ChatRoomMsgResponse
import io.sinzak.android.remote.dataclass.chat.ChatRoomResponse
import retrofit2.Call
import retrofit2.http.*

interface RemoteChatInterface {
    @POST("api/chat/rooms")
    fun getChatRooms(@HeaderMap headerMap: HashMap<String, String>) : Call<ChatRoomListResponse>

    @POST("api/chat/rooms/create")
    fun createChatRoom(@HeaderMap headerMap: HashMap<String, String>, @Body jsonObject: JsonObject): Call<ChatCreateResponse>

    @POST("api/chat/rooms/{id}")
    fun getChatroomInfo(@HeaderMap headerMap: HashMap<String, String>, @Path("id") id: String): Call<ChatRoomResponse>

    @GET("api/chat/rooms/{id}/message")
    fun getChatroomMsg(@HeaderMap headerMap: HashMap<String, String>, @Path("id") id: String): Call<ChatRoomMsgResponse>

}