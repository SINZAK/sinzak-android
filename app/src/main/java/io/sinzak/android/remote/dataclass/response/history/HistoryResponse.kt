package io.sinzak.android.remote.dataclass.response.history

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse

data class HistoryResponse(
    @SerializedName("data") val data : List<List<String>>? = null
) : CResponse()
