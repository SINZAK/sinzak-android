package io.sinzak.android.remote.dataclass.profile

import com.google.gson.annotations.SerializedName

data class Follow(
    @SerializedName("name") val name : String? = null,
    @SerializedName("picture") val picture : String? = null,
    @SerializedName("userId") val userId : String
)
