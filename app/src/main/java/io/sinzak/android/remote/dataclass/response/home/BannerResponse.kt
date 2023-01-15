package io.sinzak.android.remote.dataclass.response.home

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.local.BannerData

data class BannerResponse(
    @SerializedName("data") val banners : List<BannerData> = listOf()
) : CResponse()