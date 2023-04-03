package io.sinzak.android.remote.remotesources

import io.sinzak.android.remote.dataclass.response.home.NotificationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap

interface RemoteNotificationInterface {

    @GET("api/alarms")
    fun getNotificationList(@HeaderMap header: HashMap<String, String>) : Call<NotificationResponse>
}