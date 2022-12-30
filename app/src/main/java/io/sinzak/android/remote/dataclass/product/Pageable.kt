package io.sinzak.android.remote.dataclass.product

import com.google.gson.annotations.SerializedName

data class Pageable(
    @SerializedName("offset") val offset : Int? = null,
    @SerializedName("pageNumber") val pageNumber : Int? = null,
    @SerializedName("pageSize") val pageSize : Int? = null,
    @SerializedName("paged") val paged : Boolean? = null,
    @SerializedName("sort") val sort : Sort? = null,
    @SerializedName("unpaged") val unpaged : Boolean? = null
)