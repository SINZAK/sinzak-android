package io.sinzak.android.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderHomeCategoryBinding

class CategoryAdapter(val category : List<String>, val onCategoryClick : (String)->Unit) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    override fun getItemCount(): Int {
        return category.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(category[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_home_category, parent, false))
    }



    inner class ViewHolder(val bind : HolderHomeCategoryBinding) : RecyclerView.ViewHolder(bind.root){
        fun bind(c : String){
            bind.category = c
            bind.root.setOnClickListener {
                onCategoryClick(c)
            }
        }
    }
}