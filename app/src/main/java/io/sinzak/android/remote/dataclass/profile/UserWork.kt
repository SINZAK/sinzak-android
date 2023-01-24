package io.sinzak.android.remote.dataclass.profile

import com.google.gson.annotations.SerializedName

data class UserWork(
    @SerializedName("id") val id : String,
    @SerializedName("thumbnail") val thumbnail : String = ""
)