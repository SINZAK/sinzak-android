package io.sinzak.android.remote.dataclass.chat

import com.google.gson.annotations.SerializedName
import io.sinzak.android.utils.ChatUtil
import io.sinzak.android.utils.TimeUtil

data class ChatMsg(
    @SerializedName(ChatUtil.MESSAGE_ID) val msgId : Int = 0,
    @SerializedName(ChatUtil.MESSAGE) val msg : String = "",
    @SerializedName(ChatUtil.MESSAGE_TYPE) val type : String = ChatUtil.TYPE_TEXT,
    @SerializedName(ChatUtil.SENDER_NAME) val senderName: String = "",
    @SerializedName(ChatUtil.SENDER_ID) val senderId: String = "",
    @SerializedName(ChatUtil.SEND_AT) val sendTime: String= "",
    var isMyChat: Boolean = true
){
    override fun equals(other: Any?): Boolean {
        if(other is ChatMsg){
            return msgId == other.msgId
        }


        return super.equals(other)
    }

    fun getFormattedTime() : String {
        return TimeUtil.dateTimeToString(sendTime)
    }
}