package io.sinzak.android.ui.main.profile.setting

import android.widget.TextView
import androidx.databinding.BindingAdapter
import io.sinzak.android.enums.SDK.*

@BindingAdapter("socialOrigin")
fun setSocialOrigin(textView: TextView, origin: String)
{
    textView.text =
        when(origin)
        {
            Naver.name -> "네이버"
            Google.name -> "구글"
            Kakao.name -> "카카오"
            else -> ""
        }
}