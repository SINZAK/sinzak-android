package io.sinzak.android.ui.main.profile.setting.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderBlocklistLinearBinding
import io.sinzak.android.remote.dataclass.profile.UserProfile
import io.sinzak.android.system.LogDebug

class BlockListAdapter(
    val onButtonClick : ((String) -> Unit)? = null
) : RecyclerView.Adapter<BlockListAdapter.ViewHolder>() {

    private var blockList : List<UserProfile> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_blocklist_linear,null,true)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(blockList[position])
    }

    override fun getItemCount(): Int {
        return blockList.size
    }

    fun setBlockList(blocks : List<UserProfile>)
    {
        if (this.blockList != blocks)
        {
            val oldSize = itemCount
            this.blockList = blocks
            if (oldSize >= itemCount)
                notifyDataSetChanged()
            else
                notifyItemRangeInserted(oldSize,itemCount - oldSize)
            LogDebug(javaClass.name,"[Block List] 새로운 데이터 수신 $itemCount 개")
        }
    }

    private fun setMatchParentToRecyclerView(view: View) {
        val layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = layoutParams
    }

    inner class ViewHolder(val bind : HolderBlocklistLinearBinding) : RecyclerView.ViewHolder(bind.root)
    {
        fun bind(profile: UserProfile)
        {
            setMatchParentToRecyclerView(bind.root)
            bind.profile = profile
            bind.setOnButtonClick {
                onButtonClick!!(profile.userId)
                val deleteList = blockList.toMutableList()
                deleteList.removeAt(adapterPosition)
                blockList = deleteList
                notifyItemRemoved(adapterPosition)
            }
        }
    }


}