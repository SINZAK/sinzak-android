package io.sinzak.android.ui.main.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.databinding.HolderChatMsgReceiveBinding
import io.sinzak.android.databinding.HolderChatMsgSentBinding
import io.sinzak.android.remote.dataclass.chat.ChatMsg

class ChatMsgAdapter(val msgList: List<ChatMsg>) : RecyclerView.Adapter<ChatMsgAdapter.ViewHolder>() {



    fun notifyPageLoaded(count: Int = 10){
        notifyItemRangeInserted(0, count)
    }

    fun notifyMsgAdded(){
        notifyItemInserted(itemCount - 1)

    }

    fun notifyNewChatRoom(){
        notifyDataSetChanged()
    }


    override fun getItemId(position: Int): Long {
        return msgList[position].type.toLong()
    }

    inner class ViewHolder(val bind: ViewDataBinding) : RecyclerView.ViewHolder(bind.root){
        fun bind(msg: ChatMsg){
            if(bind is HolderChatMsgSentBinding){
                bind.msg = msg
            }

            if(bind is HolderChatMsgReceiveBinding){
                bind.msg = msg
            }




        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when(viewType){
            MSG_SENT ->
                return ViewHolder(HolderChatMsgSentBinding.inflate(LayoutInflater.from(parent.context)))
            else ->
                return ViewHolder(HolderChatMsgReceiveBinding.inflate(LayoutInflater.from(parent.context)))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(msgList[position])
    }

    override fun getItemCount(): Int {
        return msgList.size
    }


    companion object{
        const val MSG_SENT = 1
        const val MSG_RECEIVE = 0
    }
}