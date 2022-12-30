package io.sinzak.android.remote.dataclass.product

import com.google.gson.annotations.SerializedName
import io.sinzak.android.system.LogError
import io.sinzak.android.utils.DateFormats
import java.lang.Exception
import java.text.NumberFormat
import java.util.*

data class Product(
    @SerializedName("author") val author: String? = null,
    @SerializedName("complete") val complete: Boolean? = null,
    @SerializedName("content") val content: String? = null,
    @SerializedName("date") val date: String? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("like") val like: Boolean? = null,
    @SerializedName("likesCnt") val likeCnt: Int? = null,
    @SerializedName("photo") val photoUrl: String? = null,
    @SerializedName("price") val price: Int? = null,
    @SerializedName("suggest") val suggest: Boolean? = null,
    @SerializedName("title") val title: String? = null,
){
    override fun equals(other: Any?): Boolean {
        if(other is Product)
            return id == other.id

        return super.equals(other)
    }

    fun getFormattedPrice() : String?{
        price?.let{
            return NumberFormat.getNumberInstance(Locale.KOREA).format(it)+"원"
        }
        return null
    }

    fun getTimePassed() : String {
        try {
            val time = DateFormats.MySqlFormat.parse(date.toString().replace('T', ' '))
            val timeGap = (System.currentTimeMillis() - time.time)/ 1000L / 3600



            when{
                timeGap >= 365 * 24 ->
                    return "· ${(timeGap / (365 * 24)).toInt()}년전"
                timeGap >= 24 * 30->
                    return "· ${(timeGap / 24 / 30).toInt()}개월 전"
                timeGap >= 24 ->
                    return "· ${(timeGap / 24).toInt()}일 전"
                else ->
                    return "· ${timeGap.toInt()}시간 전"
            }

        } catch (e: Exception)
        {

        }
        return "· 최근"
    }

    fun getFormattedDate() : String{
        return try{
            val time = DateFormats.MySqlFormat.parse(date.toString().replace('T',' '))
            DateFormats.KoreanFormatDateOnly.format(time)
        } catch(e:Exception) {
            LogError(e)
            "최근"

        }

    }
}