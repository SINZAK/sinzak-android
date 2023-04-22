package io.sinzak.android.remote.dataclass.response.chat

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.chat.ChatMsg

data class ChatRoomMsgResponse(
    @SerializedName("content") val msgContent: List<ChatMsg>? = null
): CResponse()