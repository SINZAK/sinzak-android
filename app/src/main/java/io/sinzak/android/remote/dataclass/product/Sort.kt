package io.sinzak.android.remote.dataclass.product

import com.google.gson.annotations.SerializedName

data class Sort(
    @SerializedName("empty") val empty  : Boolean? = null,
    @SerializedName("sorted") val sorted : Boolean? = null,
    @SerializedName("unsorted") val unsorted : Boolean? = null
)