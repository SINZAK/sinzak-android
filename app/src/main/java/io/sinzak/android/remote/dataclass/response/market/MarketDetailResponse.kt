package io.sinzak.android.remote.dataclass.response.market

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.utils.TimeUtil

data class MarketDetailResponse(

    @SerializedName("category") val category: String,

    @SerializedName("chatCnt") val chatCnt: Int,
    @SerializedName("complete") val complete: Boolean = false,
    @SerializedName("content") val content: String,
    @SerializedName("date") val date: String = "2023-01-02T18:26:27",


    @SerializedName("id") val productId: String,
    @SerializedName("images") val imgUrls: List<String> = listOf(),

    @SerializedName("like") val like: Boolean = false,
    @SerializedName("likesCnt") val likeCnt: Int = 0,
    @SerializedName("price") val price: Int = 0,
    @SerializedName("suggest") val priceSuggestEnable: Boolean = false,
    @SerializedName("title") val title: String = "",
    @SerializedName("trading") val isTrading: Boolean = false,


    @SerializedName("author") val author: String,
    @SerializedName("author_picture") val authorPic: String,
    @SerializedName("cert_celeb") val certCeleb: Boolean,
    @SerializedName("cert_uni") val certUni: Boolean,
    @SerializedName("univ") val authorUniv: String = "",
    @SerializedName("followerNum") val authorFollowerCnt: Int = 0,
    @SerializedName("following") val isFollowing: Boolean = false,

    @SerializedName("width") val dWidth: Int = 0,
    @SerializedName("height") val dHeight: Int = 0,
    @SerializedName("vertical") val dVertical: Int = 0,
    @SerializedName("views") val views: Int = 0,

    @SerializedName("wish") val wish: Boolean = false,
    @SerializedName("wishCnt") val wishCnt: Int = 0
) : CResponse(){
    fun getTimePassed() : String {
        return TimeUtil.getTimePassed(date.toString())
    }
}