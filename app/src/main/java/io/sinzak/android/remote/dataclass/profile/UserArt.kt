package io.sinzak.android.remote.dataclass.profile

import com.google.gson.annotations.SerializedName
import io.sinzak.android.utils.TimeUtil

data class UserArt(
    @SerializedName("id") var id : String,
    @SerializedName("thumbnail") val thumbnail : String = "",
    @SerializedName("title") val title : String = "",
    @SerializedName("createdAt") val createdAt : String = "",
    @SerializedName("complete") val complete : Boolean = false



) {
    override fun equals(other: Any?): Boolean {
        if (other is UserArt)
            return id == other.id

        return super.equals(other)
    }

    fun getTimePassed() : String {
        return TimeUtil.getTimePassedExceptDot(createdAt)
    }

}
