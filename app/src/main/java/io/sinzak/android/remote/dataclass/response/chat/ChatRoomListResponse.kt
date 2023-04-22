package io.sinzak.android.remote.dataclass.response.chat

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.chat.ChatRoom

data class ChatRoomListResponse(
    @SerializedName("data") val data : List<ChatRoom>
) : CResponse()