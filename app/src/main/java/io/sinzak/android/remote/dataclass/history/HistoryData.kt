package io.sinzak.android.remote.dataclass.history

import com.google.gson.annotations.SerializedName

data class HistoryData (
    @SerializedName("id") val id : String,
    @SerializedName("word") val word : String = ""
)