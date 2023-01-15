package io.sinzak.android.ui.main.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderFullBannerBinding
import io.sinzak.android.databinding.HolderFullImgBinding
import io.sinzak.android.remote.dataclass.local.BannerData

class BannerAdapter(val banner : List<BannerData>,
    val gotoLogin : ()->Unit
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

            bind.banner = bannerData

            bannerData.bannerDrawableId?.let{
                id->
                bind.ivMain.setImageDrawable(context.getDrawable(id))
            }

            bannerData.bannerImageUrl?.let{
                url ->
                Glide.with(bind.ivMain).asBitmap().load(url).transform(CenterCrop()).into(bind.ivMain)
            }



            bind.root.setOnClickListener {
                when(bannerData.bannerMode){
                    BannerData.BANNER_LOGIN ->
                        gotoLogin()
                }
            }
        }
    }
}