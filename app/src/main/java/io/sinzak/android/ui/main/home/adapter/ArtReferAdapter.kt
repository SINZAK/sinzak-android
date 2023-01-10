package io.sinzak.android.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import io.sinzak.android.databinding.HolderHomeArtLinearBinding
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.system.dp

class ArtReferAdapter(val onItemClick : ((Product)->Unit)? = null) : RecyclerView.Adapter<ArtReferAdapter.ViewHolder>() {

    private var products = listOf<Product>()

    fun updateData(p : List<Product>){
        products = p
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return products.size
    }




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HolderHomeArtLinearBinding.inflate(LayoutInflater.from(parent.context))
        )
    }


    inner class ViewHolder(val bind : HolderHomeArtLinearBinding) : RecyclerView.ViewHolder(bind.root){

        fun bind(product: Product){
            bind.product = product
            bind.apply{

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