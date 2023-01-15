package io.sinzak.android.remote.dataclass.response.profile

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse

data class UserProfileResponse (
    @SerializedName("cert_uni") val cert_uni : Boolean = false,
    @SerializedName("followerNumber") val followerNumber : Int = 0,
    @SerializedName("followingNumber") val followingNumber : Int = 0,
    @SerializedName("ifFollow") val ifFollow : Boolean = false,
    @SerializedName("imageUrl") val imageUrl : String = "",
    @SerializedName("introduction") val introduction : String = "",
    @SerializedName("myProfile") val myProfile : Boolean = false,
    @SerializedName("name") val name : String ="",
    @SerializedName("univ") val univ : String = "",
    @SerializedName("userId") val userId : Int
    ) : CResponse()