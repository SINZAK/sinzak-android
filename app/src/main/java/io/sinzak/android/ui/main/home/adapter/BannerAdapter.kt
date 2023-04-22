package io.sinzak.android.ui.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderFullBannerBinding
import io.sinzak.android.remote.dataclass.local.BannerData
import io.sinzak.android.system.dp

class BannerAdapter(
    val banner : List<BannerData>,
    val showUser : (String) -> Unit
) : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return banner.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(banner[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_full_banner, parent, false), parent.context)
    }


    inner class ViewHolder(val bind : HolderFullBannerBinding, val context : Context) : RecyclerView.ViewHolder(bind.root){
        fun bind(bannerData: BannerData){

            bind.content = if (bannerData.isUserBanner()) bannerData.getUserName() else ""

            bannerData.bannerImageUrl?.let{
                url ->
                Glide.with(bind.ivMain).asBitmap().load(url)
                    .transform( CenterCrop(), RoundedCorners(20.dp.toInt())
                ).into(bind.ivMain)
            }

            bind.root.setOnClickListener {
                bannerData.showUser(showUser)
            }
        }
    }
}