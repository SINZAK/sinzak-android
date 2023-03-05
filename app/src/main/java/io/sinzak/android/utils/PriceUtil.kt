package io.sinzak.android.utils

import java.text.NumberFormat
import java.util.*

object PriceUtil {

    fun getFormattedPrice(price : Int) : String
    {
        return NumberFormat.getNumberInstance(Locale.KOREA).format(price)
    }

    fun makePriceInt(price : String) : Int
    {
        if (price.isEmpty()) return 0
        return price.replace(",","").toInt()
    }
}