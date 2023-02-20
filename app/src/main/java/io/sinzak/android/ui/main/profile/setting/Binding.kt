package io.sinzak.android.ui.main.profile.setting

import android.widget.TextView
import androidx.databinding.BindingAdapter
import io.sinzak.android.enums.SDK
import io.sinzak.android.enums.SDK.*

@BindingAdapter("currentSdk")
fun setCurrentSdk(textView: TextView, sdk: SDK)
{
    textView.text =
        when(sdk)
        {
            naver -> "네이버"
            google -> "구글"
            kakao -> "카카오"
        }
}