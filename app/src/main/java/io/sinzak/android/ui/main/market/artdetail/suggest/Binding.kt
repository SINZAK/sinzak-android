package io.sinzak.android.ui.main.market.artdetail.suggest

import android.widget.EditText
import androidx.databinding.BindingAdapter
import java.text.DecimalFormat

@BindingAdapter("addComma")
fun addCommaToNumber(editText: EditText, price : String){
    val decimalFormat = DecimalFormat("#,###")
    val priceExceptComma = price.replace(",","")
    var formattedNumber = ""
    if (priceExceptComma.isNotEmpty()) formattedNumber = decimalFormat.format(priceExceptComma.toDouble())
    editText.setText(formattedNumber)
    editText.setSelection(formattedNumber.length)
}