package io.sinzak.android.remote.dataclass.request.login

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CRequest

data class JoinRequest(
    @SerializedName("category_like") val categoryLike : String,
    @SerializedName("cert_univ") val certUniv : Boolean = false,
    @SerializedName("email") val email : String,
    @SerializedName("name") val name : String,
    @SerializedName("nickName") val nickname : String,
    @SerializedName("origin") val SDKOrigin : String,
    @SerializedName("term") val term : Boolean = false,
    @SerializedName("univ") val university : String = "",
    @SerializedName("univ_email") val univEmail : String = ""

 ) : CRequest()