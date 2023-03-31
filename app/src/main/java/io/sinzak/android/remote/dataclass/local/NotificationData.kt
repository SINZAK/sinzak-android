package io.sinzak.android.remote.dataclass.local

import com.google.gson.annotations.SerializedName
import io.sinzak.android.utils.TimeUtil

data class NotificationData(
    @SerializedName("route") val userId : String,
    @SerializedName("alarmType") val alarmType : String? = null,
    @SerializedName("date") val date : String? = null,
    @SerializedName("thumbnail") val thumbnail : String? = null,
    @SerializedName("opponentUserName") val userName : String? = null
) {
    fun getTimePassedExceptDot() : String {
        return TimeUtil.getTimePassedExceptDot(date.toString())
    }
}
