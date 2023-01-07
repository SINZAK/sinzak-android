package io.sinzak.android.remote.dataclass

import com.google.gson.annotations.SerializedName


open class CResponse(
    @SerializedName("timestamp") val timestamp : String? = null,
    @SerializedName("path") val path : String? = null,
    @SerializedName("status") val status : Int? = null,
    @SerializedName("success") val success : Boolean? = null,
    @SerializedName("message") val message : String? = null
)
