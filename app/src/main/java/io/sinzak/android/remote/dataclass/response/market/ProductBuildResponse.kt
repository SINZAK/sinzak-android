package io.sinzak.android.remote.dataclass.response.market

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse

data class ProductBuildResponse(
    @SerializedName("id") val id : Int? = null
) : CResponse()