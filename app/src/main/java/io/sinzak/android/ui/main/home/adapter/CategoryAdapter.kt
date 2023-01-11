package io.sinzak.android.ui.main.home.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderHomeCategoryBinding

class CategoryAdapter(val category : List<String>, val drawable : List<Drawable?>, val onCategoryClick : (String)->Unit) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return category.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(category[position], drawable[position]!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_home_category, parent, false))
    }



    inner class ViewHolder(val bind : HolderHomeCategoryBinding) : RecyclerView.ViewHolder(bind.root){
        fun bind(c : String, d : Drawable){
            bind.category = c
            bind.ivCategory.setImageDrawable(d)
            bind.root.setOnClickListener {
                onCategoryClick(c)
            }
        }
    }
}