package io.sinzak.android.remote.dataclass.response.login

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse

data class GoogleResponse(
    @SerializedName("access_token") var access_token : String
) : CResponse()
