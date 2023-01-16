package io.sinzak.android.remote.dataclass.response.profile

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.profile.Follow

data class FollowResponse(
    @SerializedName("name") val name : String? = null,
    @SerializedName("picture") val picture : String? = null,
    @SerializedName("userId") val userId : Int
) : CResponse()
