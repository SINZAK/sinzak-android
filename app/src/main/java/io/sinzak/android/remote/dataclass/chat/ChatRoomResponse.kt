package io.sinzak.android.remote.dataclass.chat

import com.google.gson.annotations.SerializedName
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.utils.PriceUtil

data class ChatRoomResponse(
   @SerializedName("data") val data: Data? = null
):CResponse()
{

    data class Data(
        @SerializedName("roomName") val roomName: String,
        @SerializedName("postId") val productId: Int,
        @SerializedName("postName") val productName: String,
        @SerializedName("thumbnail") val thumbnail: String,
        @SerializedName("complete") val complete: Boolean = false,
        @SerializedName("suggest") val suggest: Boolean = false,
        @SerializedName("postUserId") val userId: String = "",
        @SerializedName("opponentUserId") val opponentUserId : String = "",
        @SerializedName("price") val price : Int? = null,
        @SerializedName("postType") val postType : String
    ) {

        fun getFormattedPrice() : String?{
            price?.let{
                return PriceUtil.getFormattedPrice(it)+"원"
            }
            return null
        }

        fun getStatusString(complete: Boolean) : String {
            if (isProduct()) {
                return if (complete) "거래완료" else "거래중"
            }
            else return if (complete) "모집완료" else "모집중"
        }

        fun isProduct() : Boolean {
            return postType != "WORK"
        }
    }

}
