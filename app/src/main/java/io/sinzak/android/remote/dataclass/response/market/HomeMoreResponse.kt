package io.sinzak.android.remote.dataclass.response.market

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.product.Product

data class HomeMoreResponse(
    @SerializedName("data") val products : List<Product>? = null
) : CResponse()