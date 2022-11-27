package io.sinzak.android.ui.base

import android.graphics.Rect
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import io.sinzak.android.model.insets.SoftKeyModel
import io.sinzak.android.system.LogDebug
import io.sinzak.android.system.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@BindingAdapter("isSelect")
fun setIsSelect(view : View, status : Boolean)
{
    view.isSelected = status
}

@BindingAdapter("marginVertical","marginHorizon")
fun setRecyclerViewItemMargin(view : RecyclerView, vertical : Float = 0f, horizontal : Float = 0f)
{
    view.addItemDecoration(
        object : RecyclerView.ItemDecoration(){
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.left = horizontal.dp.toInt()
                outRect.top = vertical.dp.toInt()
                outRect.right = horizontal.dp.toInt()
                outRect.bottom = vertical.dp.toInt()

            }
        }
    )
}

@BindingAdapter("requestFocus", "soft")
fun requestFocus(view : EditText, focus : Boolean, soft : SoftKeyModel)
{
    if(focus)
    {
        view.requestFocus()
        soft.imm.showSoftInput(view,0)
    }
}


@BindingAdapter("remoteImgUrl","cornerRadius")
fun setImg(view : ImageView, url : String, radius : Float)
{
    CoroutineScope(Dispatchers.IO).launch {
        Glide.with(view).asBitmap().load(GlideUrl(url)).transform(CenterCrop(), RoundedCorners(radius.dp.toInt())).apply {
            CoroutineScope(Dispatchers.Main).launch {
                into(view)
            }
        }
    }
}