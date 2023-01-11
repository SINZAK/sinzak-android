package io.sinzak.android.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderHomeProductVerticalBinding
import io.sinzak.android.remote.dataclass.product.Product

class ArtVerticalAdapter(val products : List<Product>, val onClick : (Product)->Unit = {}) : RecyclerView.Adapter<ArtVerticalAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return products.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_home_product_vertical, parent, false)
        )
    }



    inner class ViewHolder(val bind : HolderHomeProductVerticalBinding) : RecyclerView.ViewHolder(bind.root){

        fun bind(product : Product){
            bind.product = product
            bind.root.setOnClickListener {
                onClick(product)
            }
        }
    }
}
