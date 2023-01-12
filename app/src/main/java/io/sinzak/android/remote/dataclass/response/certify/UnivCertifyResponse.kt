package io.sinzak.android.remote.dataclass.response.certify

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse

data class UnivCertifyResponse(
    @SerializedName("id") val id : String
) : CResponse()
