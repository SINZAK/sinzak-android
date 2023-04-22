package io.sinzak.android.remote.dataclass.response.chat

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse

data class ChatCreateResponse(
    @SerializedName("data") val data: NewChat?
): CResponse()
{
    data class NewChat(
        @SerializedName("roomUuid") val roomUuid : String? = "",
        @SerializedName("newChatRoom") val newChatRoom : Boolean = false
    )
}