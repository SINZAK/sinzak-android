package io.sinzak.android.remote.dataclass.response.market

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.product.Pageable
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.remote.dataclass.product.Sort

data class MarketProductResponse(
    @SerializedName("content") val products : List<Product>? = null,
    @SerializedName("empty") val empty : Boolean? = null,
    @SerializedName("first") val first : Boolean? = null,
    @SerializedName("last") val last : Boolean? = null,
    @SerializedName("number") val number : Int? = null,
    @SerializedName("numberOfElements") val numberOfElement: Int? =null,
    @SerializedName("pageable") val pageable : Pageable? = null,
    @SerializedName("size") val size : Int? = null,
    @SerializedName("totalElements") val totalElements : Int? = null,
    @SerializedName("totalPages") val totalPage : Int? = null,
    @SerializedName("sort") val sort : Sort? = null,
    ) : CResponse()