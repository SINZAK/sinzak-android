package io.sinzak.android.remote.dataclass.request.market

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CRequest

data class ProductBuildRequest(
    @SerializedName("category") val category : String? = null,
    @SerializedName("content") val content : String,
    @SerializedName("height") val height : Int? = null,
    @SerializedName("price") val price : Int? = null,
    @SerializedName("vertical") val vertical : Int? = null,
    @SerializedName("width") val width : Int? = null,
    @SerializedName("suggest") val priceSuggest : Boolean? = null,
    @SerializedName("title") val title : String,
)  : CRequest()