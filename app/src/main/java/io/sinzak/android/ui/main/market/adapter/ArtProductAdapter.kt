package io.sinzak.android.ui.main.market.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderMarketArtGridBinding
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.remote.dataclass.product.ProductListener
import io.sinzak.android.system.LogDebug
import io.sinzak.android.system.LogInfo


class ArtProductAdapter(
    val onLikeClick : ((Int, Boolean)->Unit)? = null,
    val onItemClick : ((Product)->Unit)? = null
) : RecyclerView.Adapter<ArtProductAdapter.ViewHolder>() {

    private var artProducts : List<Product> = listOf()

    override fun getItemCount(): Int {
        return artProducts.size
    }



    fun setProducts(products : List<Product>, isProductsEmpty : (Boolean)->Unit)
    {
        isProductsEmpty(products.isEmpty())

        if(this.artProducts != products)
        {
            val oldSize = itemCount
            this.artProducts = products
            if(oldSize >= itemCount)
                notifyDataSetChanged()
            else
                notifyItemRangeInserted(oldSize,itemCount - oldSize)
            LogDebug(javaClass.name,"[ART PRODUCT] 새로운 데이터 수신 $itemCount 개")
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(artProducts[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_market_art_grid,null,true)
        )
    }


    inner class ViewHolder(val bind : HolderMarketArtGridBinding) : RecyclerView.ViewHolder(bind.root), RequestListener<Bitmap>{

        fun bind(product: Product){
            bind.product = product
            bind.setOnItemClick {
                onItemClick!!(product)
            }
            bind.setOnLikeClick {
                onLikeClick?.let{c->
                    bind.product = product.toggleLike()
                    c(product.id!!, product.like!!)
                }
            }

        }

        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Bitmap>?,
            isFirstResource: Boolean
        ): Boolean {

            e?.printStackTrace()
            return false
        }

        override fun onResourceReady(
            resource: Bitmap?,
            model: Any?,
            target: Target<Bitmap>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            LogInfo(javaClass.name,"Resource : $resource")
            return false
        }



    }
}