package io.sinzak.android.remote.dataclass.response.profile

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.profile.UserProfile

data class ReportListResponse(
    @SerializedName("data") val data : List<UserProfile>? = null
) : CResponse()
