package io.sinzak.android.ui.main.market.artdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderFullImgBinding

class VpAdapter : RecyclerView.Adapter<VpAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_full_img,parent,false)
        )
    }

    inner class ViewHolder(val bind : HolderFullImgBinding) : RecyclerView.ViewHolder(bind.root){
        fun bind(){
            bind.imgUrl = "https://images.unsplash.com/photo-1618005198919-d3d4b5a92ead?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8M3x8M2QlMjBhcnR8ZW58MHx8MHx8&w=1000&q=80"
        }
    }
}