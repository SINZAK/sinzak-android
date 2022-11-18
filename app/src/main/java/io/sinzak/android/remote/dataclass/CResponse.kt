package io.sinzak.android.remote.dataclass

import com.google.gson.annotations.SerializedName


open class CResponse(
    @SerializedName("status") val status : String = "",
    @SerializedName("error") val error : Boolean? = false,
    @SerializedName("message") val message : String? = null
)
