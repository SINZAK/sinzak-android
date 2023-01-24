package io.sinzak.android.remote.dataclass.profile

import com.google.gson.annotations.SerializedName

data class UserProduct(
    @SerializedName("id") val id : String,
    @SerializedName("thumbnail") val thumbnail : String = ""
)