package io.sinzak.android.remote.dataclass.response.market

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.product.Product

class MarketHomeResponse(
    @SerializedName("data") val data : Data
) : CResponse(){

    data class Data(
        @SerializedName("trading") val tradingProducts : List<Product>? = null,
        @SerializedName("recommend") val recommends : List<Product>? = null,
        @SerializedName("new") val newProducts : List<Product>? = null,
        @SerializedName("hot") val hotProducts : List<Product>? = null,
        @SerializedName("following") val followingProducts : List<Product>? = null
    )
}