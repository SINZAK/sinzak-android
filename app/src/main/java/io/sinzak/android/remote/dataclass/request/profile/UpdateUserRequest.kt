package io.sinzak.android.remote.dataclass.request.profile

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CRequest

data class UpdateUserRequest(
    @SerializedName("introduction") val introduction : String,
    @SerializedName("name") val name : String,
    @SerializedName("picture") val picture : String,
) : CRequest()