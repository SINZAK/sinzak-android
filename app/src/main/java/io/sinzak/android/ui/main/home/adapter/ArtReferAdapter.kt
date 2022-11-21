package io.sinzak.android.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import io.sinzak.android.databinding.HolderHomeArtReferBinding
import io.sinzak.android.system.dp

class ArtReferAdapter : RecyclerView.Adapter<ArtReferAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return 3
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HolderHomeArtReferBinding.inflate(LayoutInflater.from(parent.context))
        )
    }


    inner class ViewHolder(val bind : HolderHomeArtReferBinding) : RecyclerView.ViewHolder(bind.root){

        fun bind(){
            bind.apply{
                Glide.with(ivPoster).asBitmap().load(GlideUrl("https://wallpaperaccess.com/full/2339301.jpg"))
                    .transform(CenterCrop(),RoundedCorners(10.dp.toInt())).into(ivPoster)
            }
        }
    }
}