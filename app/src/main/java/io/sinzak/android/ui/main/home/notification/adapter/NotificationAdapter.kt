package io.sinzak.android.ui.main.home.notification.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderNotificationBinding

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {


    inner class ViewHolder(val bind : HolderNotificationBinding) : RecyclerView.ViewHolder(bind.root)
    {
        fun bind(){
            bind.img = "https://img.freepik.com/premium-vector/abstract-modern-natural-background-with-shapes-drawing-art_544963-325.jpg"
        }
    }


    override fun getItemCount(): Int {
        return 5
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_notification,parent,false)
        )
    }
}