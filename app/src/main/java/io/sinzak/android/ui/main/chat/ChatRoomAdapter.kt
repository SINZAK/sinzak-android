package io.sinzak.android.ui.main.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderChatRoomBinding
import io.sinzak.android.remote.dataclass.chat.ChatRoom

class ChatRoomAdapter(
    val onClickRoom: (ChatRoom)->Unit
) : RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>() {


    var chatList = listOf<ChatRoom>()


    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(chatList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_chat_room,parent,false)
        )
    }

    fun removeByUUID(uuid: String)
    {
        chatList = chatList.filter { it.roomUuid != uuid }
        notifyDataSetChanged()
    }




    inner class ViewHolder(val bind : HolderChatRoomBinding) : RecyclerView.ViewHolder(bind.root)
    {
        fun bind(chat : ChatRoom)
        {
            bind.chat = chat
            bind.root.setOnClickListener {
                onClickRoom(chat)
            }
        }
    }
}