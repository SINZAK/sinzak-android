package io.sinzak.android.ui.main.market.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderHomeArtGridBinding
import io.sinzak.android.databinding.HolderHomeArtLinearBinding
import io.sinzak.android.databinding.HolderMarketArtGridBinding
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.system.dp

class ArtsAdapter : RecyclerView.Adapter<ArtsAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return 8
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.holder_home_art_grid,
                null,
                true
            )
        )
    }


    inner class ViewHolder(val bind: HolderHomeArtGridBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun bind(){
            bind.apply{
                Glide.with(ivPoster).asBitmap().load(GlideUrl("https://wallpaperaccess.com/full/2339301.jpg"))
                    .transform(CenterCrop(),RoundedCorners(10.dp.toInt())).into(ivPoster)
            }
        }
    }
}
