package io.sinzak.android.ui.main.market.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderMarketFilterBinding

class FilterAdapter : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {



    private var filterChosenItem = mutableListOf<String>()
    private var filterItems = mutableListOf<String>()

    override fun getItemCount(): Int {
        return filterItems.size + filterChosenItem.size + 1
    }

    private var selectFilter : ((String?,Boolean)->Unit)? = null

    fun reduce(chosen : MutableList<String>, filter : MutableList<String>, listener : (String?,Boolean)->Unit)
    {
        filterChosenItem = chosen
        filterItems = filter
        selectFilter = listener
        notifyItemRangeChanged(0,itemCount)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position == 0)
        {
            holder.bindAllBtn()
            return
        }
        if(position <= filterChosenItem.size)
        {
            holder.bindBtnChosen(filterChosenItem[position - 1])
            return
        }
        holder.bindBtn(filterItems[position - 1 - filterChosenItem.size])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_market_filter,null,true))
    }


    inner class ViewHolder(val bind : HolderMarketFilterBinding) : RecyclerView.ViewHolder(bind.root){

        fun bindAllBtn(){
            bind.filter = "전체"
            bind.select = filterChosenItem.size == 0
            bind.root.setOnClickListener {
                selectFilter?.run{this(null,true)}
            }
        }

       fun bindBtnChosen(filter : String)
        {
            bind.filter = filter
            bind.select = true
            bind.root.setOnClickListener {
                selectFilter?.run{this(filter,false)}
            }
        }

        fun bindBtn(filter : String)
        {
            bind.filter = filter
            bind.select = false
            bind.root.setOnClickListener {
                selectFilter?.run{this(filter,true)}
            }
        }

    }
}