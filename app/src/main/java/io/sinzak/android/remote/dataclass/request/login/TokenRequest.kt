package io.sinzak.android.remote.dataclass.request.login

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CRequest
import io.sinzak.android.remote.dataclass.CResponse

open class TokenRequest(
    @SerializedName("accessToken") val accessToken : String? = null,
    @SerializedName("refreshToken") val refreshToken : String? = null,
) : CRequest()
