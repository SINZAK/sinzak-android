package io.sinzak.android.ui.main.profile.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.sinzak.android.databinding.HolderProfileArtLinearBinding
import io.sinzak.android.remote.dataclass.profile.UserProduct
import io.sinzak.android.remote.dataclass.profile.UserWork
import io.sinzak.android.system.LogInfo

class ProfileArtAdapter(
    val type : Int
): RecyclerView.Adapter<ProfileArtAdapter.ViewHolder>() {

    companion object {
        const val TYPE_PRODUCT = 0
        const val TYPE_WORK = 1
    }

    private var products = listOf<UserProduct>()
    private var works = listOf<UserWork>()

    fun setProductData(p : List<UserProduct>)
    {
        products = p
        notifyDataSetChanged()
    }

    fun setWorkData(w : List<UserWork>)
    {
        works = w
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (type== TYPE_PRODUCT) holder.bindProduct(product = products[position])
        else if (type == TYPE_WORK) holder.bindWork(work = works[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HolderProfileArtLinearBinding.inflate(LayoutInflater.from(parent.context))
        )
    }


    inner class ViewHolder(val bind : HolderProfileArtLinearBinding) : RecyclerView.ViewHolder(bind.root), RequestListener<Bitmap>{

        fun bindProduct(product: UserProduct) {
            bind.type = TYPE_PRODUCT
            bind.product = product
        }

        fun bindWork(work: UserWork) {
            bind.type = TYPE_WORK
            bind.work = work
        }

        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<Bitmap>?,
            isFirstResource: Boolean
        ): Boolean {
            e?.printStackTrace()
            return false
        }

        override fun onResourceReady(
            resource: Bitmap?,
            model: Any?,
            target: Target<Bitmap>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            LogInfo(javaClass.name,"Resource : $resource")
            return false
        }

    }

}