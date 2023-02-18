package io.sinzak.android.remote.dataclass.response.profile

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.product.Product
import io.sinzak.android.remote.dataclass.profile.UserProfile

data class UserProfileResponse (
    @SerializedName("data") val data : ProfileData? = null
) : CResponse() {

    data class ProfileData(
        @SerializedName("works") val works : List<Product>? = null,
        @SerializedName("profile") val profile: UserProfile,
        @SerializedName("products") val products : List<Product>? = null,
        @SerializedName("workEmploys") val workEmploys : List<Product>? = null
    )
}