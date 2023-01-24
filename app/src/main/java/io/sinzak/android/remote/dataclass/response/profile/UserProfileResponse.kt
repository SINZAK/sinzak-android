package io.sinzak.android.remote.dataclass.response.profile

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.profile.UserProduct
import io.sinzak.android.remote.dataclass.profile.UserProfile
import io.sinzak.android.remote.dataclass.profile.UserWork

data class UserProfileResponse (
    @SerializedName("data") val data : ProfileData? = null
) : CResponse() {

    data class ProfileData(
        @SerializedName("works") val works : List<UserWork>? = null,
        @SerializedName("profile") val profile: UserProfile,
        @SerializedName("products") val products : List<UserProduct>? = null
    )
}