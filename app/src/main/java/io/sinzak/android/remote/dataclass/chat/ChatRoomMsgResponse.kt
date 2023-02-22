package io.sinzak.android.remote.dataclass.chat

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse

data class ChatRoomMsgResponse(
    @SerializedName("content") val msgContent: List<ChatMsg>? = null
): CResponse()