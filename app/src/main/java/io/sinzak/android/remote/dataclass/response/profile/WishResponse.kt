package io.sinzak.android.remote.dataclass.response.profile

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.product.Product

data class WishResponse(
    @SerializedName("data") val data : WishData? = null
) : CResponse() {

    data class WishData(
        @SerializedName("workWishes") val workWishes : List<Product>? = null,
        @SerializedName("productWishes") val productWishes : List<Product>? = null,
    )
}
