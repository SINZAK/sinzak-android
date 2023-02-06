package io.sinzak.android.remote.dataclass.profile

import com.google.gson.annotations.SerializedName

data class UserArt(
    @SerializedName("id") val id : String,
    @SerializedName("thumbnail") val thumbnail : String = "",
    @SerializedName("title") val title : String = "",
    @SerializedName("createdAt") val createdAt : String = "",
    @SerializedName("complete") val complete : Boolean = false
)
