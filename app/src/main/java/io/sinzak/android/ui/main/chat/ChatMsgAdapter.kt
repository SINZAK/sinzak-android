package io.sinzak.android.ui.main.chat

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderChatMsgReceiveBinding
import io.sinzak.android.databinding.HolderChatMsgReceiveImgBinding
import io.sinzak.android.databinding.HolderChatMsgSentBinding
import io.sinzak.android.databinding.HolderChatMsgSentImgBinding
import io.sinzak.android.remote.dataclass.chat.ChatMsg

class ChatMsgAdapter(val msgList: List<ChatMsg>) : RecyclerView.Adapter<ChatMsgAdapter.ViewHolder>() {

    var rv: RecyclerView? = null

    fun notifyPageLoaded(count: Int = 10){
        notifyItemRangeInserted(0, count)
    }

    fun notifyMsgAdded(count: Int){
        notifyItemRangeInserted(itemCount - count,count)
        rv?.smoothScrollBy(0, (Resources.getSystem().displayMetrics.density * 60).toInt())

    }

    fun notifyNewChatRoom(){
        notifyDataSetChanged()
        rv?.scrollBy(0,999999)
    }


    override fun getItemViewType(position: Int): Int {
        msgList[position].let{msg->
            return if(msg.isMyChat){
                if(msg.type == "IMAGE")
                    MSG_SENT_IMG
                else
                    MSG_SENT
            } else {
                if(msg.type == "IMAGE")
                    MSG_RECEIVE_IMG
                else
                    MSG_RECEIVE
            }
        }

    }

    inner class ViewHolder(val bind: ViewDataBinding) : RecyclerView.ViewHolder(bind.root){
        fun bind(msg: ChatMsg){
            if(bind is HolderChatMsgSentBinding){
                bind.msg = msg
            }

            if(bind is HolderChatMsgReceiveBinding){
                bind.msg = msg
            }

            if(bind is HolderChatMsgSentImgBinding){
                bind.msg = msg
            }

            if(bind is HolderChatMsgReceiveImgBinding){
                bind.msg = msg
            }




        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            when(viewType){
                MSG_SENT -> R.layout.holder_chat_msg_sent
                MSG_RECEIVE -> R.layout.holder_chat_msg_receive
                MSG_RECEIVE_IMG -> R.layout.holder_chat_msg_receive_img
                else -> R.layout.holder_chat_msg_sent_img


            }

            , parent, false))


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
        const val MSG_SENT_IMG = 3
        const val MSG_RECEIVE_IMG = 2
    }
}