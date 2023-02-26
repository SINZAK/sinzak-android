package io.sinzak.android.remote.dataclass.chat

import com.google.gson.annotations.SerializedName
import io.sinzak.android.utils.TimeUtil

data class ChatRoom(
    val unreadCount : Int? = 0,
    @SerializedName("roomName") val sender : String? = "김지호",
    @SerializedName("latestMessage") val lastMsg : String? = "네 감사합니다 ~",
    @SerializedName("univ") val school : String? = "홍익대",
    @SerializedName("latestMessageTime") val time : String? = "1시간 전",
    val userVerified : Boolean? = true,
    @SerializedName("image") val image: String = "",
    @SerializedName("roomUuid")val roomUuid: String? = ""
){
    fun getFormattedTime(): String{
        return TimeUtil.getTimePassed(time.toString())
    }
}