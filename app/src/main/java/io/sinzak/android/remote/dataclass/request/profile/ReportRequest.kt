package io.sinzak.android.remote.dataclass.request.profile

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CRequest

data class ReportRequest(
    @SerializedName("userId") val userId : String,
    @SerializedName("reason") val reason : String
) : CRequest()