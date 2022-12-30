package io.sinzak.android.remote.dataclass.request.login

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CRequest

data class LoginEmailBody(
    @SerializedName("email") val email : String
) : CRequest()