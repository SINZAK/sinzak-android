package io.sinzak.android.ui.main.market.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderHomeArtGridBinding
import io.sinzak.android.databinding.HolderHomeArtLinearBinding
import io.sinzak.android.databinding.HolderMarketArtGridBinding
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.system.LogDebug
import io.sinzak.android.system.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArtProductAdapter : RecyclerView.Adapter<ArtProductAdapter.ViewHolder>() {

    private var artProducts : List<Product> = listOf()

    override fun getItemCount(): Int {
        return artProducts.size
    }

    fun setProducts(products : List<Product>)
    {
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
            CoroutineScope(Dispatchers.Main).launch {
                bindImg(product.photoUrl)
            }
        }

        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Bitmap>?,
            isFirstResource: Boolean
        ): Boolean {
            bindImg("https://wallpaperaccess.com/full/2339301.jpg")
            return true
        }

        override fun onResourceReady(
            resource: Bitmap?,
            model: Any?,
            target: Target<Bitmap>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            return false
        }

        fun bindImg(url : String?)
        {

            bind.apply{
                Glide.with(ivPoster).asBitmap().load(url)
                    .transform(CenterCrop(),RoundedCorners(10.dp.toInt())).addListener(this@ViewHolder).into(ivPoster)
            }
        }


    }
}