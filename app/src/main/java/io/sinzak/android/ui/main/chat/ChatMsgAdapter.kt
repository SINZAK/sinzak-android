package io.sinzak.android.ui.main.chat

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderChatMsgReceiveBinding
import io.sinzak.android.databinding.HolderChatMsgSentBinding
import io.sinzak.android.remote.dataclass.chat.ChatMsg

class ChatMsgAdapter(val msgList: List<ChatMsg>) : RecyclerView.Adapter<ChatMsgAdapter.ViewHolder>() {

    var rv: RecyclerView? = null

    fun notifyPageLoaded(count: Int = 10){
        notifyItemRangeInserted(0, count)
    }

    fun notifyMsgAdded(count: Int){
        notifyItemRangeInserted(itemCount - count,count)
        rv?.smoothScrollBy(0, (Resources.getSystem().displayMetrics.density * 45).toInt())

    }

    fun notifyNewChatRoom(){
        notifyDataSetChanged()
    }


    override fun getItemViewType(position: Int): Int {
        return if(msgList[position].isMyChat) MSG_SENT else MSG_RECEIVE
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
                return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_chat_msg_sent, parent, false))
            else ->
                return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_chat_msg_receive, parent, false))
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