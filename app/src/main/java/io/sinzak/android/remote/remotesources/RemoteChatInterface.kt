package io.sinzak.android.remote.remotesources

import io.sinzak.android.remote.dataclass.chat.ChatRoomListResponse
import retrofit2.Call
import retrofit2.http.HeaderMap
import retrofit2.http.POST

interface RemoteChatInterface {
    @POST("api/chat/rooms")
    fun getChatRooms(@HeaderMap headerMap: HashMap<String, String>) : Call<ChatRoomListResponse>
}