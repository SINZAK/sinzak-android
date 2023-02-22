package io.sinzak.android.remote.dataclass.request.profile

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CRequest

data class UpdateInterestRequest(
    @SerializedName("categoryLike") val categoryLike : String
) : CRequest()
