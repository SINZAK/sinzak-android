package io.sinzak.android.remote.dataclass.response.login

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse

data class NaverProfile(
    @SerializedName("resultCode") val resultCode : String? = null,
    @SerializedName("response") val profile : Profile? = null
) : CResponse() {
    data class Profile(
        @SerializedName("email") val email : String? = null
    )
}