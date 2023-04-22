package io.sinzak.android.remote.dataclass.response.chat

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse

data class ChatImageUploadResponse(
    @SerializedName("data") val data: List<Data>? = null
):CResponse() {

    data class Data(
        @SerializedName("url") val imageUrl: String
    )
}