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
            naver.name -> "네이버"
            google.name -> "구글"
            kakao.name -> "카카오"
            else -> "에러"
        }
}