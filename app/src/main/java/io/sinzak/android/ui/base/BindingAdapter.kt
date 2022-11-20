package io.sinzak.android.ui.base

import android.view.View
import androidx.databinding.BindingAdapter
import io.sinzak.android.system.LogDebug


@BindingAdapter("isSelect")
fun setIsSelect(view : View, status : Boolean)
{
    view.isSelected = status
}