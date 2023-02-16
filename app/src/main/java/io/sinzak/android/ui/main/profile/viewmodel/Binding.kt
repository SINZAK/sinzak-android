package io.sinzak.android.ui.main.profile.viewmodel

import android.text.Html
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("highLightText")
fun highLightText(view : TextView, input : String)
{
    view.text = Html.fromHtml(input,Html.FROM_HTML_MODE_LEGACY).toString()
}