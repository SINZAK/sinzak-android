package io.sinzak.android.remote.dataclass.history

import com.google.gson.annotations.SerializedName

data class HistoryData (
    val history : String = ""
) {
    private fun splitHistoryData(history: String, index : Int) : String
    {
        val splitData = history.split(",")
        return splitData[index].trim()
    }

    fun getId() : String {
        return splitHistoryData(history,0)
    }
    fun getWord() : String {
        return splitHistoryData(history, 1)
    }
}