package io.sinzak.android.remote.dataclass.request.certify

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CRequest

data class MailRequest(
    @SerializedName("univ_email") val univ_email : String,
    @SerializedName("code") val code : String?,
    @SerializedName("univName") val univName : String,
) : CRequest()
