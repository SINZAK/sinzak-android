package io.sinzak.android.remote.dataclass.chat

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse

data class ChatRoomListResponse(
    @SerializedName("data") val data : List<ChatRoom>
) : CResponse()