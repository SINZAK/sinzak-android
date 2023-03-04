package io.sinzak.android.ui.main.profile.viewmodel

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.util.regex.Pattern

@BindingAdapter("highLightText")
fun highlightText(textView: TextView, input : String) {
    val spannableString = SpannableString(input)

    // @로 시작하는 모든 단어에 대해 스타일 적용
    val atPattern = Pattern.compile("@(\\w+)")
    val atMatcher = atPattern.matcher(input)
    while (atMatcher.find()) {
        val start = atMatcher.start()
        val end = atMatcher.end()
        val blueColor = Color.parseColor("#0B3A82")
        val blueSpan = ForegroundColorSpan(blueColor)
        spannableString.setSpan(blueSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    // https://로 시작하는 모든 단어에 대해 스타일 적용
    val urlPattern = Patterns.WEB_URL
    val urlMatcher = urlPattern.matcher(input)
    while (urlMatcher.find()) {
        val start = urlMatcher.start()
        val end = urlMatcher.end()
        val blueColor = Color.parseColor("#0B3A82")
        val blueSpan = ForegroundColorSpan(blueColor)
        spannableString.setSpan(blueSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    textView.text = spannableString
}