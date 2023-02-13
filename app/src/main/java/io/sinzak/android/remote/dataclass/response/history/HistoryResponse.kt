package io.sinzak.android.remote.dataclass.response.history

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.history.HistoryData

data class HistoryResponse(
    @SerializedName("data") val data : List<HistoryData>? = null
) : CResponse()
