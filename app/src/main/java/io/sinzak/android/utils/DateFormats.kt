package io.sinzak.android.utils

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateFormats {



    companion object
    {
        val MySqlFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
        val KoreanFormatDateOnly = SimpleDateFormat("yy년 MM월 dd일", Locale.KOREA)
    }
}