package io.sinzak.android.remote.dataclass.chat

data class ChatRoom(
    val unreadCount : Int? = 3,
    val sender : String? = "김지호",
    val lastMsg : String? = "네 감사합니다 ~",
    val school : String? = "홍익대",
    val time : String? = "1시간 전",
    val userVerified : Boolean? = true,
    val roomUuid: String? = ""
)