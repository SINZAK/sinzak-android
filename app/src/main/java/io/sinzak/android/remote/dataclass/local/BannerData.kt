package io.sinzak.android.remote.dataclass.local

data class BannerData(
    val bannerMode : Int = BANNER_MAIN,
    val bannerImageUrl : String? = null,
    val bannerDrawableId : Int? = null
){

    companion object{
        const val BANNER_LOGIN = 1
        const val BANNER_MAIN = 0
    }
}