package io.sinzak.android.model.notification

import io.sinzak.android.constants.API_GET_NOTIFICATION_LIST
import io.sinzak.android.model.BaseModel
import io.sinzak.android.remote.dataclass.CResponse
import io.sinzak.android.remote.dataclass.local.NotificationData
import io.sinzak.android.remote.dataclass.response.home.NotificationResponse
import io.sinzak.android.remote.retrofit.CallImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationModel @Inject constructor() : BaseModel() {

    private val _notificationList = MutableStateFlow(mutableListOf<NotificationData>())
    val notificationList : StateFlow<MutableList<NotificationData>> get() = _notificationList

    fun getRemoteNotificationList() {
        _notificationList.value = mutableListOf()
        CallImpl(
            API_GET_NOTIFICATION_LIST,
            this
        ).apply {
            remote.sendRequestApi(this)
        }
    }

    private fun onNotificationResponse(response : NotificationResponse) {
        response.notifications?.let { notifications ->
            _notificationList.value = notifications.toMutableList()
        }
    }



    override fun onConnectionSuccess(api: Int, body: CResponse) {
        when(api) {
            API_GET_NOTIFICATION_LIST -> {
                onNotificationResponse(body as NotificationResponse)
            }
        }
    }

    override fun handleError(api: Int, msg: String?, t: Throwable?) {

    }
}