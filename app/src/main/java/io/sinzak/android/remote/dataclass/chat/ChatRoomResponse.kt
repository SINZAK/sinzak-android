package io.sinzak.android.remote.dataclass.chat

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse

data class ChatRoomResponse(
   @SerializedName("data") val data: Data? = null
):CResponse(){

    data class Data(
        @SerializedName("roomName") val roomName: String,
        @SerializedName("productId") val productId: Int,
        @SerializedName("productName") val productName: String,
        @SerializedName("thumbnail") val thumbnail: String,
        @SerializedName("complete") val complete: Boolean = false,
        @SerializedName("suggest") val suggest: Boolean = false,
        @SerializedName("userId") val userId: String = ""
    )
}