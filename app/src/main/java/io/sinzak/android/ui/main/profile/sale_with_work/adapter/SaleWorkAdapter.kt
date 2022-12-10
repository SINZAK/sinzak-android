package io.sinzak.android.ui.main.profile.sale_with_work.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import io.sinzak.android.databinding.HolderSaleArtLinearBinding
import io.sinzak.android.databinding.HolderWorkArtLinearBinding
import io.sinzak.android.system.dp

class SaleWorkAdapter(): RecyclerView.Adapter<SaleWorkAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HolderWorkArtLinearBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int {
        return 3
    }

    private fun setMatchParentToRecycelrView(view: View) {
        val layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = layoutParams
    }


    inner class ViewHolder(val bind : HolderWorkArtLinearBinding) : RecyclerView.ViewHolder(bind.root){

        fun bind() {

            setMatchParentToRecycelrView(bind.root)

            bind.apply {
                Glide.with(ivSaleImg).asBitmap()
                    .load(
                        GlideUrl(
                            "https://wallpaperaccess.com/full/2339301.jpg")
                    )
                    .transform(CenterCrop(), RoundedCorners(12.dp.toInt())).into(ivSaleImg)
            }
        }
    }


}