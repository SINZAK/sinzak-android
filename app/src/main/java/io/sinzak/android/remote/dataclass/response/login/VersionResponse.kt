package io.sinzak.android.remote.dataclass.response.login

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse

data class VersionResponse(
    @SerializedName("data") val versionCode : Int? = null
) : CResponse()
