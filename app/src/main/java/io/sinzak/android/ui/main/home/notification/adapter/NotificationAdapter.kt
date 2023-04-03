package io.sinzak.android.ui.main.home.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderNotificationBinding
import io.sinzak.android.remote.dataclass.local.NotificationData

class NotificationAdapter(
    val onItemClick : ((String) -> Unit)? = null,
) : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private var notificationList : List<NotificationData> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_notification,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notificationList[position])
    }

    fun setNotifications(notifications : List<NotificationData>)
    {
        notificationList = listOf()
        notificationList = notifications
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }


    inner class ViewHolder(val bind : HolderNotificationBinding) : RecyclerView.ViewHolder(bind.root)
    {
        fun bind(notification : NotificationData){
            bind.notification = notification
            bind.setOnItemClick {
                onItemClick!!(notification.userId)
            }
        }
    }


}