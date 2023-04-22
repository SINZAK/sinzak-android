package io.sinzak.android.remote.remotesources

import com.google.gson.JsonObject
import io.sinzak.android.remote.dataclass.chat.*
import io.sinzak.android.remote.dataclass.response.chat.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface RemoteChatInterface {
    @POST("api/chat/rooms")
    fun getChatRooms(@HeaderMap headerMap: HashMap<String, String>) : Call<ChatRoomListResponse>

    @POST("api/chat/rooms/create")
    fun createChatRoom(@HeaderMap headerMap: HashMap<String, String>, @Body jsonObject: JsonObject): Call<ChatCreateResponse>

    @POST("api/chat/rooms/{id}")
    fun getChatroomInfo(@HeaderMap headerMap: HashMap<String, String>, @Path("id") id: String): Call<ChatRoomResponse>

    @GET("api/chat/rooms/{id}/message")
    fun getChatroomMsg(@HeaderMap headerMap: HashMap<String, String>, @Path("id") id: String): Call<ChatRoomMsgResponse>


    @POST("api/chat/rooms/post")
    fun getChatRoomFromPost(@HeaderMap headerMap: HashMap<String, String>, @Body body: JsonObject): Call<ChatRoomListResponse>

    @Multipart
    @POST("api/chat/rooms/{id}/image")
    fun postChatImg(@HeaderMap headerMap: HashMap<String, String>, @Path("id") id: String, @Part body: List<MultipartBody.Part>): Call<ChatImageUploadResponse>

    @POST("api/chat/rooms/check")
    fun checkRoomExist(@HeaderMap headerMap: HashMap<String, String>, @Body jsonObject: JsonObject) : Call<ChatRoomCheckResponse>
}