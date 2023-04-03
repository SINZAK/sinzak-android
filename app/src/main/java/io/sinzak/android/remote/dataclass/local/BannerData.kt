package io.sinzak.android.remote.dataclass.local

import com.google.gson.annotations.SerializedName

data class BannerData(
    var bannerMode : Int = BANNER_MAIN,
    @SerializedName("id") val id : String,
    @SerializedName("content") val content : String? = "",
    @SerializedName("imageUrl") val bannerImageUrl : String? = null,
    @SerializedName("href") val userId : String? = null
){

    fun isUserBanner() : Boolean {
        return !userId.isNullOrEmpty()
    }

    fun showUser(onClick : (String)->Unit) {
        if (isUserBanner()) {
            onClick(userId!!.split("/").last())
        }
    }

    companion object{
        const val BANNER_MAIN = 0
        const val BANNER_LOGIN = 1
        const val BANNER_USER = 2
    }
}