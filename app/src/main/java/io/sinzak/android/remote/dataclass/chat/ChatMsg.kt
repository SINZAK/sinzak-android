package io.sinzak.android.remote.dataclass.chat

import com.google.gson.annotations.SerializedName

data class ChatMsg(
    @SerializedName("msgId") val msgId : Int = 0,
    @SerializedName("msg") val msg : String = "",
    @SerializedName("type") val type : Int = 0
){
    override fun equals(other: Any?): Boolean {
        if(other is ChatMsg){
            return msgId == other.msgId
        }


        return super.equals(other)
    }
}