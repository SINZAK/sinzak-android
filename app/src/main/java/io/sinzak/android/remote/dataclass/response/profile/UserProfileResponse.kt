package io.sinzak.android.remote.dataclass.response.profile

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse

data class UserProfileResponse (
    @SerializedName("followerNumber") val followerNumber : String = "",
    @SerializedName("followingNumber") val followingNumber : String = "",
    @SerializedName("introduction") val introduction : String = "",
    @SerializedName("myProfile") var myProfile : Boolean = false,
    @SerializedName("name") val name : String ="",
    @SerializedName("userId") val userId : String
    ) : CResponse()