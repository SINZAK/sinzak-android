package io.sinzak.android.remote.dataclass.response.chat

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse

data class ChatRoomCheckResponse(
    @SerializedName("data") val data : RoomCheck? = null
) :CResponse() {
    data class RoomCheck(
        @SerializedName("roomUuid") val uuid : String? = null,
        @SerializedName("exist") val exist : Boolean
    )
}
