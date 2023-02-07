package io.sinzak.android.remote.dataclass.response.login

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse

data class GoogleResponse(
    @SerializedName("access_token") val access_token : String,
    @SerializedName("expires_in") val expires_in : String,
    @SerializedName("id_token") val id_token : String
) : CResponse()
