package io.sinzak.android.ui.main.profile.art.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderWorkArtLinearBinding
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.system.LogDebug

class SaleWorkAdapter(
    val completeTradeClick : ((String,Boolean) -> Unit)? = null,
    val onItemClick : ((Product) -> Unit)? = null,
    val isComplete : Boolean = false,
): RecyclerView.Adapter<SaleWorkAdapter.ViewHolder>() {

    private var productList : List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_work_art_linear,null,true)
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


    inner class ViewHolder(val bind : HolderWorkArtLinearBinding) : RecyclerView.ViewHolder(bind.root){

        fun bind(product: Product) {

            setMatchParentToRecyclerView(bind.root)
            bind.product = product
            bind.setItemClick {
                onItemClick!!(product)
            }
            bind.setCompleteTradeClick {
                completeTradeClick!!(product.id.toString(), product.complete!!)
            }
            bind.isComplete = isComplete
        }
    }


}