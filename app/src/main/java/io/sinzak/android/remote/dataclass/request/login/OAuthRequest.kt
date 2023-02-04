package io.sinzak.android.remote.dataclass.request.login

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CRequest

data class OAuthRequest(
    @SerializedName("accessToken") private val accessToken : String,
    @SerializedName("idToken") private val idToken : String,
    @SerializedName("origin") private val origin : String
) : CRequest()
