package io.sinzak.android.ui.main.profile.art.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderProfileArtLinearBinding
import io.sinzak.android.model.profile.ProfileModel
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.system.LogDebug
import kotlinx.coroutines.flow.StateFlow

class SaleWorkAdapter(
    val completeTradeClick : ((String) -> Unit)? = null,
    val onItemClick : ((Product) -> Unit)? = null,
    val isComplete : StateFlow<Boolean>,
    val viewType: Int,
    val itemType : Boolean,
    val model : ProfileModel
): RecyclerView.Adapter<SaleWorkAdapter.ViewHolder>() {

    private var productList : List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_profile_art_linear,null,true)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(product = productList[position])
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun setArts (arts : List<Product>)
    {
        if (this.productList != arts)
        {
            val oldSize = itemCount
            this.productList = arts

            if (oldSize >= itemCount) notifyDataSetChanged()
            else notifyItemRangeInserted(oldSize,itemCount - oldSize)

            LogDebug(javaClass.name,"[Art List] 새로운 데이터 수신 $itemCount 개")
        }
    }

    private fun setMatchParentToRecyclerView(view: View) {
        val layoutParams = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = layoutParams
    }


    inner class ViewHolder(val bind : HolderProfileArtLinearBinding) : RecyclerView.ViewHolder(bind.root){

        fun bind(product: Product) {

            setMatchParentToRecyclerView(bind.root)
            bind.product = product
            bind.setItemClick {
                onItemClick!!(product)
            }
            bind.setCompleteTradeClick {
                completeTradeClick!!(product.id.toString())
            }
            bind.isComplete = isComplete.value
            bind.completeText = setCompleteText(viewType)
            bind.isMine = model.profile.value!!.myProfile
            bind.itemType = itemType
        }
    }

    private fun setCompleteText(viewType: Int) : String
    {
        var completeText = ""
        when(viewType)
        {
            0 -> completeText = PRODUCT
            1 -> completeText = WORK
            2 -> completeText = REQUEST
        }

        return completeText
    }

    companion object {
        const val PRODUCT = "판매완료"
        const val WORK = "작업완료"
        const val REQUEST = "의뢰완료"
    }


}