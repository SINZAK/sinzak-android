package io.sinzak.android.ui.base

import android.graphics.Rect
import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import io.sinzak.android.model.insets.SoftKeyModel
import io.sinzak.android.system.LogDebug
import io.sinzak.android.system.dp


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