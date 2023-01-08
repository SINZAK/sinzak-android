package io.sinzak.android.remote.dataclass.request.market

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CRequest

data class ProductBuildRequest(
    @SerializedName("category") val category : String,
    @SerializedName("content") val content : String,
    @SerializedName("height") val height : Int,
    @SerializedName("price") val price : Int,
    @SerializedName("vertical") val vertical : Int,
    @SerializedName("width") val width : Int,
    @SerializedName("suggest") val priceSuggest : Boolean,
    @SerializedName("title") val title : String,
)  : CRequest()