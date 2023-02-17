package io.sinzak.android.remote.remotesources

import com.google.gson.JsonObject
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.chat.ChatCreateResponse
import io.sinzak.android.remote.dataclass.chat.ChatRoomListResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface RemoteChatInterface {
    @POST("api/chat/rooms")
    fun getChatRooms(@HeaderMap headerMap: HashMap<String, String>) : Call<ChatRoomListResponse>

    @POST("api/chat/rooms/create")
    fun createChatRoom(@HeaderMap headerMap: HashMap<String, String>, @Body jsonObject: JsonObject): Call<ChatCreateResponse>
}