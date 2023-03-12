package io.sinzak.android.remote.dataclass.request.market

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CRequest

data class ProductBuildRequest(
    // 공통
    @SerializedName("category") val category : String? = null,
    @SerializedName("content") val content : String,
    @SerializedName("suggest") val priceSuggest : Boolean? = null,
    @SerializedName("title") val title : String,

    // 작품 판매
    @SerializedName("price") val price : Int? = null,
    @SerializedName("height") val height : Int? = null,
    @SerializedName("vertical") val vertical : Int? = null,
    @SerializedName("width") val width : Int? = null,

    //의뢰,작업
    @SerializedName("employment") val employment : Boolean? = null

)  : CRequest()