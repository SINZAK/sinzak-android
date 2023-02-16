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
    @SerializedName("imageUrl") val imageUrl : String = "",
    @SerializedName("univ") val univ : String = "",
    @SerializedName("ifFollow") val ifFollow : Boolean = false
) {

    fun getHighLightIntro() : String
    {
        return highlightWords(introduction,"#OB3A82")
    }

    /**
     * 특정 단어에 색을 입힙니다
     */
    private fun highlightWords(input: String, color: String): String {
        val words = input.split(" ")
        var result = ""
        for (word in words) {
            if (word.startsWith("@") || word.startsWith("https://")) {
                result += "<font color='$color'>$word</font> "
            } else {
                result += "$word "
            }
        }
        return result.trim()
    }
}