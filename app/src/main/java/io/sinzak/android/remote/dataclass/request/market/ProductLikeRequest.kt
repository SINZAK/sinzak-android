package io.sinzak.android.remote.dataclass.request.market

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CRequest

data class ProductLikeRequest(
    @SerializedName("id") val id : Int,
    @SerializedName("mode") val mode : Boolean
) : CRequest()