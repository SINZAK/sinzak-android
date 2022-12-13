package io.sinzak.android.ui.main.market.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderMarketFilterBinding

class FilterAdapter(private val filterItems : MutableList<String>, val selectFilter : ((List<String>)->Unit)? = null) : RecyclerView.Adapter<FilterAdapter.ViewHolder>() {




    private var filterChosenItem = mutableListOf<String>()
    private var filterNotChosen = mutableListOf<String>()

    init{
        filterNotChosen.addAll(filterItems)
    }

    override fun getItemCount(): Int {
        return filterItems.size + 1
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
        holder.bindBtn(filterNotChosen[position - 1 - filterChosenItem.size])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_market_filter,null,true))
    }


    inner class ViewHolder(val bind : HolderMarketFilterBinding) : RecyclerView.ViewHolder(bind.root){

        fun bindAllBtn(){
            bind.filter = "전체"
            bind.select = filterChosenItem.size == 0
            bind.root.setOnClickListener {
                onClickFilter(null,true)
            }
        }

       fun bindBtnChosen(filter : String)
        {
            bind.filter = filter
            bind.select = true
            bind.root.setOnClickListener {
                onClickFilter(filter,false)
            }
        }

        fun bindBtn(filter : String)
        {
            bind.filter = filter
            bind.select = false
            bind.root.setOnClickListener {
                onClickFilter(filter,true)
            }
        }

    }


    fun onClickFilter(filter : String?, status : Boolean)
    {
        filter?.run{
            if(status)
            {
                filterNotChosen.remove(filter)
                filterChosenItem.add(filter)
            }
            else{
                filterNotChosen.add(filter)
                filterChosenItem.remove(filter)
            }
        }?:run{
            filterNotChosen.clear()
            filterNotChosen.addAll(filterItems)
            filterChosenItem.clear()
        }
        notifyItemRangeChanged(0,itemCount)
        selectFilter?.run{this(filterChosenItem)}
    }
}