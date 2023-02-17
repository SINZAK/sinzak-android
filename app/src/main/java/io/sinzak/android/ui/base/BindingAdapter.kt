package io.sinzak.android.ui.base

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import io.sinzak.android.R
import io.sinzak.android.model.insets.SoftKeyModel
import io.sinzak.android.system.LogDebug
import io.sinzak.android.system.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception


@BindingAdapter("isSelect")
fun setIsSelect(view: View, status: Boolean) {
    view.isSelected = status
}

@BindingAdapter("marginVertical", "marginHorizon")
fun setRecyclerViewItemMargin(view: RecyclerView, vertical: Float = 0f, horizontal: Float = 0f) {
    view.addItemDecoration(
        object : RecyclerView.ItemDecoration() {
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
fun requestFocus(view: EditText, focus: Boolean, soft: SoftKeyModel) {
    if (focus) {
        view.requestFocus()
        soft.imm.showSoftInput(view, 0)
    }
}

@BindingAdapter("removeFocus", "soft_")
fun removeFocus(view: EditText, focus: Boolean, soft: SoftKeyModel) {
    if (focus) {
        view.windowToken?.let {
            soft.imm.hideSoftInputFromWindow(it, 0)
        }
        view.clearFocus()

    }
}

@BindingAdapter("onFocused")
fun onFocused(view: EditText, action: View.OnClickListener) {
    view.setOnFocusChangeListener { view, t ->
        if (t)
            action.onClick(view)
    }
}


@BindingAdapter("remoteImgUrl", "cornerRadius")
fun setImg(view: ImageView, url: String?, radius: Float) {
    if (url.isNullOrEmpty()) {
        view.setImageDrawable(
            AppCompatResources.getDrawable(
                view.context,
                R.drawable.ic_img_null_holder
            )
        )
        return
    }

    view.findViewTreeLifecycleOwner()?.let { lifecycleOwner ->
        lifecycleOwner.lifecycleScope.launch {
            Glide.with(view).asBitmap().load(GlideUrl(url))
                .transform(CenterCrop(), RoundedCorners(radius.dp.toInt())).apply {
                lifecycleOwner.lifecycleScope.launch {
                    into(view)
                }
            }
        }
    }

}

@BindingAdapter("remoteImgUrl", "imgRadius")
fun setEditImg(view: ImageView, url: String?, radius: Float) {

    if (url.isNullOrEmpty()){
        view.setImageDrawable(
            AppCompatResources.getDrawable(
                view.context,
                R.drawable.ic_user_temp
            )
        )
        return
    }

    view.findViewTreeLifecycleOwner()?.let { lifecycleOwner ->
        lifecycleOwner.lifecycleScope.launch {
            Glide.with(view).asBitmap().load(GlideUrl(url))
                .transform(CenterCrop(), RoundedCorners(radius.dp.toInt())).apply {
                    lifecycleOwner.lifecycleScope.launch {
                        into(view)
                    }
                }
        }
    }

}

@BindingAdapter("remoteImgUrl")
fun setImg(view: ImageView, url: String?) {
    url ?: run {
        return
    }
    CoroutineScope(Dispatchers.IO).launch {
        Glide.with(view).asBitmap().load(GlideUrl(url)).transform(CenterCrop()).apply {
            CoroutineScope(Dispatchers.Main).launch {
                into(view)
            }
        }
    }
}


@BindingAdapter("viewpager", "adapter")
fun provideViewpager(
    view: DotsIndicator,
    viewPager: ViewPager2,
    adapter: RecyclerView.Adapter<*>?
) {
    adapter?.let {
        viewPager.adapter = adapter
        view.attachTo(viewPager)
    }

}

@BindingAdapter("onActionDone")
fun onActionDone(view: EditText, listener: View.OnClickListener) {
    view.setOnEditorActionListener { view, i, keyEvent ->
        when (i) {
            EditorInfo.IME_ACTION_DONE,
            EditorInfo.IME_ACTION_SEARCH,
            EditorInfo.IME_ACTION_NEXT,
            EditorInfo.IME_ACTION_SEND -> {

                listener.onClick(view)

                if(i != EditorInfo.IME_ACTION_SEND) {
                    val im =
                        view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    im.hideSoftInputFromWindow(view.windowToken, 0)
                    view.clearFocus()
                }else{
                    view.text = ""
                }

                return@setOnEditorActionListener true
            }
        }

        false
    }
}

@BindingAdapter("app:tint")
fun imageTint(view: ImageView, color: Int) {
    view.imageTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("app:drawableTint")
fun drawableTint(view: TextView, color: Int) {
    TextViewCompat.setCompoundDrawableTintList(view, ColorStateList.valueOf(color))
}


@BindingAdapter("app:attachTo")
fun attachToRecyclerView(view: DotsIndicator, viewPager: ViewPager2) {


    fun attach(view: DotsIndicator, viewPager: ViewPager2) {
        try {
            view.attachTo(viewPager)
        } catch (e: Exception) {
            CoroutineScope(Dispatchers.Main).launch {
                attach(view, viewPager)
            }
        }
    }

    attach(view, viewPager)


}


@BindingAdapter("app:bottomReached")
fun recyclerViewBottomReached(view: RecyclerView, listener: View.OnClickListener) {
    view.addOnScrollListener(
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (view.computeVerticalScrollOffset() >= view.computeVerticalScrollRange() - view.computeVerticalScrollExtent())
                    listener.onClick(view)
            }
        }
    )
}