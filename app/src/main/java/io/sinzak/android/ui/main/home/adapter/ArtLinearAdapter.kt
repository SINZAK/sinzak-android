package io.sinzak.android.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import io.sinzak.android.databinding.HolderHomeArtLinearBinding
import io.sinzak.android.databinding.HolderHomeLinearNextBinding
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.system.dp

class ArtLinearAdapter(val onNextClick : ()->Unit = {},
                       val onItemClick : ((Product)->Unit)? = null,
) : RecyclerView.Adapter<ArtLinearAdapter.ViewHolder>() {

    private var products = listOf<Product>()

    fun updateData(p : List<Product>){
        products = p
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return products.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if(position == itemCount - 1)
            return 1
        return 0
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position == itemCount - 1)
            holder.bind()
        else
            holder.bind(products[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType == 1)
            return ViewHolder(
                HolderHomeLinearNextBinding.inflate(LayoutInflater.from(parent.context))
            )
        return ViewHolder(
            HolderHomeArtLinearBinding.inflate(LayoutInflater.from(parent.context))
        )
    }


    inner class ViewHolder : RecyclerView.ViewHolder{

        private val bind : ViewDataBinding

        constructor(bind : HolderHomeArtLinearBinding) : super(bind.root){
            this.bind = bind
        }

        constructor(bind : HolderHomeLinearNextBinding) : super(bind.root){
            this.bind = bind
        }

        fun bind(){
            bind as HolderHomeLinearNextBinding
            bind.setOnClick {
                onNextClick()
            }
        }

        fun bind(product: Product){
            bind as HolderHomeArtLinearBinding
            bind.product = product
            bind.apply{
                if(!product.thumbnail.isNullOrEmpty())
                Glide.with(ivPoster).asBitmap().load(GlideUrl(product.thumbnail))
                    .transform(CenterCrop(),RoundedCorners(10.dp.toInt())).into(ivPoster)


                root.setOnClickListener {
                    onItemClick?.let{onItemClick ->
                        onItemClick(product)
                    }
                }
            }
        }
    }
}