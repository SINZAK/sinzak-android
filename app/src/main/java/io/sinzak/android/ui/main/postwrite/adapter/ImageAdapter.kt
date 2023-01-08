package io.sinzak.android.ui.main.postwrite.adapter

import android.content.res.Resources
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderWriteImgBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ImageAdapter(val img : MutableList<Uri>, val removeImg : (Uri)->Unit) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {


    val touchHelperCallback = ItemTouchListener()


    val isEmpty get() = img.isEmpty()

    override fun getItemCount(): Int {
        return if (isEmpty) 5
        else img.size
    }

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        if(isEmpty){
            holder.bindNull()
            return
        }
        holder.bindImg(img[position],position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_write_img,parent,false)
        )
    }


    inner class ViewHolder(val bind : HolderWriteImgBinding) : RecyclerView.ViewHolder(bind.root){

        fun bindNull(){
            bind.isFirst = false
            bind.ivImg.setImageDrawable(bind.ivImg.context.getDrawable(R.drawable.ic_img_null_holder))
            bind.onDelete = null
            bind.btnDelete.visibility = View.INVISIBLE
        }

        fun bindImg(uri : Uri, idx : Int){

            bind.isFirst = idx == 0
            bind.btnDelete.visibility = View.VISIBLE

            bind.setOnDelete {
                val idx = img.indexOf(uri)
              removeImg(uri)
                if(idx == 0)
                    notifyItemRangeChanged(0,itemCount)
                else
                    notifyItemRemoved(idx)


            }

            CoroutineScope(Dispatchers.Main).launch {
                try{
                    Glide.with(bind.ivImg).asBitmap().load(uri).transform(RoundedCorners(
                        (Resources.getSystem().displayMetrics.density * 8f).toInt()
                    )).into(bind.ivImg)
                }
                catch(e:Exception){

                }
            }
        }
    }



    inner class ItemTouchListener : ItemTouchHelper.Callback(){
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val dragFlags = ItemTouchHelper.START or ItemTouchHelper.END
            val swipeFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            return makeMovementFlags(dragFlags, swipeFlags)
        }



        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val fromPosition = recyclerView.getChildAdapterPosition(viewHolder.itemView)
            val toPosition = recyclerView.getChildAdapterPosition(target.itemView)

            if (fromPosition < toPosition) {
                for (i in fromPosition until toPosition) {
                    Collections.swap(img, i, i + 1)
                }
            } else {
                for (i in fromPosition downTo toPosition + 1) {
                    Collections.swap(img, i, i - 1)
                }
            }
            notifyItemMoved(fromPosition, toPosition)


            adjustHolderNum(recyclerView)
            return true
        }

        fun adjustHolderNum(rv : RecyclerView){
            rv.children.forEach {
                val order = rv.getChildAdapterPosition(it)
                val vh = (rv.getChildViewHolder(it) as ViewHolder)
                if(order == 0 && vh.bind.isFirst == false){
                    vh.bind.isFirst = true
                    vh.bind.notifyChange()
                }

                if(order != 0 && vh.bind.isFirst == true){
                    vh.bind.isFirst = false
                    vh.bind.notifyChange()
                }
            }
        }



        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        }



        override fun isLongPressDragEnabled(): Boolean {
            return true
        }
    }
}

@BindingAdapter("adapter")
fun setAdapter(view : RecyclerView, adapter : ImageAdapter)
{
    view.adapter = adapter
    ItemTouchHelper(adapter.touchHelperCallback).attachToRecyclerView(view)
}