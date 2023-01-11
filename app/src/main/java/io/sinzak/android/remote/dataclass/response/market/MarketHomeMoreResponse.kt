package io.sinzak.android.remote.dataclass.response.market

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.product.Product

class MarketHomeMoreResponse(
    @SerializedName("products") val tradingProducts : List<Product>? = null,

) : CResponse()