package io.sinzak.android.utils

import com.gmail.bishoybasily.stomp.lib.StompClient
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.sinzak.android.constants.CODE_USER_ID
import io.sinzak.android.constants.CODE_USER_NAME
import io.sinzak.android.remote.dataclass.chat.ChatMsg
import io.sinzak.android.remote.retrofit.BASE_URL
import io.sinzak.android.system.App.Companion.prefs
import okhttp3.OkHttpClient


class ChatUtil(
    val roomId: String,
    val onReceiveMsg: (ChatMsg) -> Unit,
    val onSuccessSendMsg: (Boolean) -> Unit
    ) {




    val client = StompClient(OkHttpClient(), 1000L).apply{
        url = "${BASE_URL}/stomp/chat"
    }

    val messageSubscriber = client.join("/sub/chat/rooms/${roomId}").subscribe {
        try{
            val chatMsg = Gson().fromJson(it.toString(), ChatMsg::class.java)
            onReceiveMsg(chatMsg)
        }catch(e:Exception){

        }
    }

    fun destroySession(){
        messageSubscriber.dispose()

    }


    fun sendMsg(msg: String){

        val jsonObject = JsonObject().apply{
            addProperty(MESSAGE, msg)
            addProperty(ROOM_ID, roomId)
            addProperty(SENDER_ID, prefs.getString(CODE_USER_ID,""))
            addProperty(SENDER_NAME, prefs.getString(CODE_USER_NAME,""))
            addProperty(MESSAGE_TYPE, TYPE_TEXT)
        }


        client.send("/pub/chat/message", jsonObject.toString()).subscribe {
            onSuccessSendMsg(it)

        }.dispose()


    }


    companion object{
        const val MESSAGE = "message"
        const val SENDER_NAME = "senderName"
        const val ROOM_ID = "roomId"
        const val SENDER_ID = "senderId"
        const val MESSAGE_TYPE = "messageType"
        const val TYPE_TEXT = "TEXT"
        const val MESSAGE_ID = "messageId"
        const val SEND_AT = "sendAt"
        const val TYPE_IMAGE = "IMAGE"
    }

}