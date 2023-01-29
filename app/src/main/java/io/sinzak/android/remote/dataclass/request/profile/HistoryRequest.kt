package io.sinzak.android.remote.dataclass.request.profile

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CRequest

data class HistoryRequest(
    @SerializedName("id") val id : String
) : CRequest()
