package io.sinzak.android.remote.dataclass.response.market

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.product.Product

class MarketHomeResponse(
    @SerializedName("trading") val tradingProducts : List<Product>? = null,
    @SerializedName("new") val newProducts : List<Product>? = null,
    @SerializedName("hot") val hotProducts : List<Product>? = null
) : CResponse()