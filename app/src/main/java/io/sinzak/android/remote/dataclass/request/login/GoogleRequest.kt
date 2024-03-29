package io.sinzak.android.remote.dataclass.request.login

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CRequest

data class GoogleRequest(
    @SerializedName("grant_type") private val grant_type : String,
    @SerializedName("client_id") private val client_id : String,
    @SerializedName("client_secret") private val client_secret : String,
    @SerializedName("code") private val code : String
) : CRequest()
