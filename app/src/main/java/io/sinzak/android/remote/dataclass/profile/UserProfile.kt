package io.sinzak.android.remote.dataclass.profile

import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("userId") val userId : String,
    @SerializedName("myProfile") val myProfile : Boolean = false,
    @SerializedName("name") val name : String ="",
    @SerializedName("introduction") val introduction : String = "",
    @SerializedName("cert_uni") val cert_uni : Boolean = false,
    @SerializedName("followerNumber") val followerNumber : Int = 0,
    @SerializedName("followingNumber") val followingNumber : Int = 0,
    @SerializedName("imageUrl") val imageUrl : String? = "",
    @SerializedName("univ") val univ : String = "",
    @SerializedName("follow") val isFollow : Boolean = false,
    @SerializedName("categoryLike") val categoryLike : String? = null,
    @SerializedName("portFolioUrl") val portFolioUrl : String = "",
    @SerializedName("cert_celeb") val cert_celeb : Boolean = false,

    /**
     * 차단 리스트 용
     */
    @SerializedName("nickName") val nickName : String? = null,
    @SerializedName("picture") val picture : String? = null
)