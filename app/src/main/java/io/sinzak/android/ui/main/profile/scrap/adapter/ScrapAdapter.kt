package io.sinzak.android.ui.main.profile.scrap.adapter

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
import io.sinzak.android.databinding.HolderProfileScrapGridBinding
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.system.LogDebug

class ScrapAdapter(
    val onItemClick : ((Product)->Unit)? = null
) : RecyclerView.Adapter<ScrapAdapter.ViewHolder>() {

    private var scrapList : List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_profile_scrap_grid,null,true)
        )
    }

    fun setScraps(products : List<Product>)
    {
        if(this.scrapList != products)
        {
            val oldSize = itemCount
            this.scrapList = products
            if(oldSize >= itemCount)
                notifyDataSetChanged()
            else
                notifyItemRangeInserted(oldSize,itemCount - oldSize)
            LogDebug(javaClass.name,"[SCRAP] 새로운 데이터 수신 $itemCount 개")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(scrapList[position])
    }

    override fun getItemCount(): Int {
        return scrapList.size
    }

    inner class ViewHolder(val bind : HolderProfileScrapGridBinding)
        :RecyclerView.ViewHolder(bind.root), RequestListener<Bitmap> {

        fun bind(product: Product)
        {
            bind.product = product
            bind.setOnItemClick {
                onItemClick!!(product)
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
            return false
        }

    }

}