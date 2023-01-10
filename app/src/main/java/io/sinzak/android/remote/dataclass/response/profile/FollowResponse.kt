package io.sinzak.android.remote.dataclass.response.profile

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.profile.Follow

data class FollowResponse(
    val followList: List<Follow> = listOf()
) : CResponse()
