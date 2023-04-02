package io.sinzak.android.remote.dataclass.profile

import com.google.gson.annotations.SerializedName

data class UserProfile(
    @SerializedName("userId") val userId : String,
    @SerializedName("myProfile") val myProfile : Boolean = false,
    @SerializedName("name") val name : String ="",
    @SerializedName("introduction") val introduction : String = "",
    @SerializedName("followerNumber") val followerNumber : Int = 0,
    @SerializedName("followingNumber") val followingNumber : Int = 0,
    @SerializedName("imageUrl") val imageUrl : String? = "",
    @SerializedName("univ") val univ : String = "",
    @SerializedName("follow") val isFollow : Boolean = false,
    @SerializedName("categoryLike") val categoryLike : String? = null,
    @SerializedName("cert_uni") val cert_uni : Boolean = false, //학교 인증 여부
    @SerializedName("cert_author") val cert_author : Boolean = false, //인증 작가 여부
    @SerializedName("univcardStatus") val univStatus : String? = null, //학생증 인증 상태
    @SerializedName("celebStatus") val authorStatus : String? = null, //인증 작가 상태

    /**
     * 차단 리스트 용
     */
    @SerializedName("nickName") val nickName : String? = null,
    @SerializedName("picture") val picture : String? = null
) {

    fun isCertUnivIdProcess() : Boolean {
        return if (univStatus != null) univStatus == PROCESS else false
    }

    fun isVerifyProcess() : Boolean {
        return if (authorStatus != null) authorStatus == PROCESS else false
    }

    companion object {
        const val COMPLETE = "COMPLETE"
        const val PROCESS = "PROCESS"
        const val YET = "YET"
    }
}