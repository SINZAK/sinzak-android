package io.sinzak.android.ui.main.postwrite.adapter

import android.content.res.Resources
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import io.sinzak.android.R
import io.sinzak.android.databinding.HolderWriteImgBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ImageAdapter(val img : MutableList<Uri>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {




    override fun getItemCount(): Int {
        return img.size
    }

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        holder.bindImg(img[position],position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.holder_write_img,parent,false)
        )
    }


    inner class ViewHolder(val bind : HolderWriteImgBinding) : RecyclerView.ViewHolder(bind.root){

        fun bindImg(uri : Uri, idx : Int){

            bind.isFirst = idx == 0

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
}