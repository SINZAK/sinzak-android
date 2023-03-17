package io.sinzak.android.remote.dataclass.request.certify

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CRequest

data class UnivCertifyRequest(
    @SerializedName("univ") val univ : String,
    @SerializedName("univ_email") val univ_email : String,
) : CRequest()
