package io.sinzak.android.ui.base

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
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

@BindingAdapter("remoteImgUrl")
fun setImg(view : ImageView, url : String)
{
    CoroutineScope(Dispatchers.IO).launch {
        Glide.with(view).asBitmap().load(GlideUrl(url)).transform(CenterCrop()).apply {
            CoroutineScope(Dispatchers.Main).launch {
                into(view)
            }
        }
    }
}


@BindingAdapter("viewpager","adapter")
fun provideViewpager(view : DotsIndicator, viewPager : ViewPager2, adapter : RecyclerView.Adapter<*>?)
{
    adapter?.let{
        viewPager.adapter = adapter
        view.attachTo(viewPager)
    }

}

@BindingAdapter("onActionDone")
fun onActionDone(view : EditText, listener : View.OnClickListener)
{
    view.setOnEditorActionListener { view, i, keyEvent ->
        when(i)
        {
            EditorInfo.IME_ACTION_DONE, EditorInfo.IME_ACTION_SEARCH, EditorInfo.IME_ACTION_NEXT ->{

                listener.onClick(view)
                val im = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                im.hideSoftInputFromWindow(view.windowToken,0)
                view.clearFocus()

                return@setOnEditorActionListener true
            }
        }

        false
    }
}