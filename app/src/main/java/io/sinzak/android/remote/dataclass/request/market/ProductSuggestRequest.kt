package io.sinzak.android.remote.dataclass.request.market

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CRequest

data class ProductSuggestRequest(
    @SerializedName("id") val id : Int,
    @SerializedName("price") val price : Int
) : CRequest()