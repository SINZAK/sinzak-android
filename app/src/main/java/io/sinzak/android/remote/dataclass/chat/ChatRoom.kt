package io.sinzak.android.remote.dataclass.chat

import com.google.gson.annotations.SerializedName
import io.sinzak.android.utils.TimeUtil

data class ChatRoom(
    val unreadCount : Int? = 0,
    @SerializedName("roomName") val sender : String? = null,
    @SerializedName("latestMessage") val lastMsg : String? = null,
    @SerializedName("univ") val school : String? = null,
    @SerializedName("latestMessageTime") val time : String? = null,
    @SerializedName("image") val image: String = "",
    @SerializedName("roomUuid")val roomUuid: String? = "",
    val userVerified : Boolean? = false,

){


    fun getFormattedTime(): String{
        return TimeUtil.getTimePassedExceptDot(time.toString())
    }
}