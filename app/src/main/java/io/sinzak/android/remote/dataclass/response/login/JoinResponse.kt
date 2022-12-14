package io.sinzak.android.remote.dataclass.response.login

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse

data class JoinResponse(
    @SerializedName("token") val token : Token? = null
) : CResponse()