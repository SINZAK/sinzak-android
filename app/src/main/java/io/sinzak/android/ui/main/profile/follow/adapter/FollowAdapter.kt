package io.sinzak.android.ui.main.profile.follow.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderFollowListLinearBinding
import io.sinzak.android.remote.dataclass.profile.Follow
import io.sinzak.android.remote.dataclass.response.profile.FollowResponse
import io.sinzak.android.system.LogDebug

class FollowAdapter(
    val onItemClick : ((String) -> Unit)? = null,
) : RecyclerView.Adapter<FollowAdapter.ViewHolder>() {

    private var followList : List<Follow> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_follow_list_linear, null, true)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(followList[position])
    }

    override fun getItemCount(): Int {
        return followList.size
    }

    fun setFollows(follows : List<Follow>)
    {
        if (this.followList != follows)
        {
            val oldSize = itemCount
            this.followList = follows
            if (oldSize >= itemCount)
                notifyDataSetChanged()
            else
                notifyItemRangeInserted(oldSize,itemCount - oldSize)
            LogDebug(javaClass.name,"[Follow List] 새로운 데이터 수신 $itemCount 개")
        }
    }

    private fun setMatchParentToRecyclerView(view: View) {
        val layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = layoutParams
    }

    inner class ViewHolder(val bind : HolderFollowListLinearBinding) : RecyclerView.ViewHolder(bind.root) , RequestListener<Bitmap>
    {

        fun bind(follow: Follow)
        {
            setMatchParentToRecyclerView(bind.root)
            bind.follow = follow
            bind.setOnItemClick {
                onItemClick!!(follow.userId)
            }
            bind.setOnFollowClick {
            }
        }


        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Bitmap>?,
            isFirstResource: Boolean
        ): Boolean {
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