package io.sinzak.android.remote.dataclass.response.login

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse

open class Token(
    @SerializedName("accessToken") val accessToken : String? = null,
    @SerializedName("accessTokenExpireDate") val accessTokenExpireDate : String? = null,
    @SerializedName("refreshToken") val refreshToken : String? = null,
) : CResponse()
