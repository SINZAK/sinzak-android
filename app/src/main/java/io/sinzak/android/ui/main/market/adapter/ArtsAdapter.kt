package io.sinzak.android.ui.main.market.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
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

class ArtsAdapter(
    val products : MutableList<Product> = mutableListOf(),
    val likeClick : ((Int, Boolean) -> Unit)? = null,
    val itemClick : (Product) -> Unit = {}
) : RecyclerView.Adapter<ArtsAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return products.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.holder_market_art_grid,
                null,
                true
            )
        )
    }


    inner class ViewHolder(val bind: HolderMarketArtGridBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun bind(p : Product){
            bind.apply{
                tvPrice.isVisible = false
                this.product = p
                setOnItemClick {
                    itemClick(p)
                }
                setOnLikeClick {
                    likeClick?.let{c->
                        p.like = !p.like!!
                        p.likeCnt = p.likeCnt!! + if(p.like!!) 1 else -1
                        product = p
                        c(p.id!!, p.like!!)
                    }
                }
            }
        }
    }
}
