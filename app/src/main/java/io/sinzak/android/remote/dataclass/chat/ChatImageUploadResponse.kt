package io.sinzak.android.remote.dataclass.chat

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse

data class ChatImageUploadResponse(
    @SerializedName("data") val data: Data? = null
):CResponse() {

    data class Data(
        @SerializedName("url") val imageUrls: List<String>
    )
}