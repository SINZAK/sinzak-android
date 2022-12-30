package io.sinzak.android.remote.dataclass.product

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("author") val author: String? = null,
    @SerializedName("complete") val complete: Boolean? = null,
    @SerializedName("content") val content: String? = null,
    @SerializedName("date") val date: String? = null,
    @SerializedName("id") val id: String? = null,
    @SerializedName("like") val like: Boolean? = null,
    @SerializedName("likesCnt") val likeCnt: Int? = null,
    @SerializedName("photo") val photoUrl: String? = null,
    @SerializedName("price") val price: Int? = null,
    @SerializedName("suggest") val suggest: Boolean? = null,
    @SerializedName("title") val title: String? = null,
)