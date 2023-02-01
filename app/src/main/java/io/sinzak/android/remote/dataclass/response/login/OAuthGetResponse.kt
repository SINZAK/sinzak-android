package io.sinzak.android.remote.dataclass.response.login

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse

data class OAuthGetResponse(
    @SerializedName("data") val data: Data? = null
) : CResponse(){


    data class Data(
        @SerializedName("name") val name: String? = "",
        @SerializedName("email") val email: String? = "",
        @SerializedName("picture") val picture: String? = ""
    )
}