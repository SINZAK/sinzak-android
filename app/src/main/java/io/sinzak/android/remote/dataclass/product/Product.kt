package io.sinzak.android.remote.dataclass.product

import com.google.gson.annotations.SerializedName
import io.sinzak.android.system.LogError
import io.sinzak.android.utils.DateFormats
import io.sinzak.android.utils.PriceUtil
import io.sinzak.android.utils.TimeUtil
import java.lang.Exception
import java.text.NumberFormat
import java.util.*

data class Product(
    @SerializedName("author") val author: String? = null,
    @SerializedName("complete") var complete: Boolean? = null,
    @SerializedName("content") val content: String? = null,
    @SerializedName("date") val date: String? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("like") var like: Boolean? = null,
    @SerializedName("likesCnt") var likeCnt: Int? = null,
    @SerializedName("popularity") val popularity : Int? = null,
    @SerializedName("thumbnail") val thumbnail: String? = null,
    @SerializedName("price") val price: Int? = null,
    @SerializedName("suggest") val suggest: Boolean? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("authorUniv") val authorUniv : String? = null,
){

    fun getLickCntFit() : String{
        return if((likeCnt ?: 0) < 1000)
            likeCnt.toString()
        else
            "${((likeCnt ?: 0)/100).toFloat() / 10}K"
    }

    override fun equals(other: Any?): Boolean {
        if(other is Product)
            return id == other.id

        return super.equals(other)
    }

    fun getFormattedPrice() : String?{
        price?.let{
            return PriceUtil.getFormattedPrice(it)+"원"
        }
        return null
    }

    fun getTimePassed() : String {
        return TimeUtil.getTimePassed(date.toString())
    }

    fun getTimePassedExceptDot() : String {
        return TimeUtil.getTimePassedExceptDot(date.toString())
    }

    fun toggleLike() : Product{
        like = !like!!
        likeCnt = likeCnt!! + if(like!!) 1 else -1
        return this
    }

    fun makeAuthorLengthLimit(limit: Int) : String {
        val authorLength = author?.length ?: 0
        return if (authorLength > limit) {
            "${author!!.substring(0,limit)}.."
        } else author ?: ""
    }
}