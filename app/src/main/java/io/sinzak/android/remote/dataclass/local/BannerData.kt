package io.sinzak.android.remote.dataclass.local

import com.google.gson.annotations.SerializedName

data class BannerData(
    val bannerMode : Int = BANNER_MAIN,
    @SerializedName("title") val title : String? = "",
    @SerializedName("content") val content : String? = "",
    @SerializedName("imageUrl") val bannerImageUrl : String? = null,
    val bannerDrawableId : Int? = null
){

    companion object{
        const val BANNER_LOGIN = 1
        const val BANNER_MAIN = 0
    }
}