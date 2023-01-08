package io.sinzak.android.ui.main.market.artdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderFullImgBinding

class VpAdapter : RecyclerView.Adapter<VpAdapter.ViewHolder>() {


    var imgs = listOf<String>()

    override fun getItemCount(): Int {
        return imgs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imgs[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_full_img,parent,false)
        )
    }

    inner class ViewHolder(val bind : HolderFullImgBinding) : RecyclerView.ViewHolder(bind.root){
        fun bind(url : String){
            bind.imgUrl = url
        }
    }
}