package io.sinzak.android.ui.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderSearchHistoryBinding
import io.sinzak.android.remote.dataclass.history.HistoryData
import javax.inject.Inject

class HistoryAdapter @Inject constructor() : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private var data = listOf<HistoryData>()

    private var onClick : OnClick? = null
    private var onDelete : OnClick? = null

    fun reduce(onClick: OnClick, onDelete : OnClick, data : List<HistoryData>) : HistoryAdapter{

        if(data != this.data) {
            this.data = data
            notifyDataSetChanged()
        }
        if(onClick != this.onClick)
        {
            this.onClick = onClick
        }
        if(onDelete != this.onDelete)
        {
            this.onDelete = onDelete
        }

        return this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_search_history,parent,false))
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }




    inner class ViewHolder(val bind : HolderSearchHistoryBinding) : RecyclerView.ViewHolder(bind.root){
        fun bind(tag : HistoryData)
        {
            bind.text = tag.word
            bind.setDeleteItem {
                notifyItemRemoved(adapterPosition)
                onDelete?.onClick(tag.id)
            }
            bind.setSearch {
                onClick?.onClick(tag.word)
            }

        }
    }

    fun interface OnClick{
        fun onClick(history : String)
    }
}