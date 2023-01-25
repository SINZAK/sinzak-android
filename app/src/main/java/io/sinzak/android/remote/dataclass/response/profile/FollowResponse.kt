package io.sinzak.android.remote.dataclass.response.profile

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.profile.Follow

data class FollowResponse(
    @SerializedName("data") val follows : List<Follow>? = null
) : CResponse()
