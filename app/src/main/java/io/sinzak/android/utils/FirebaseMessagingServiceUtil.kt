package io.sinzak.android.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.sinzak.android.R
import io.sinzak.android.system.LogDebug
import io.sinzak.android.ui.main.MainActivity

class FirebaseMessagingServiceUtil : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        LogDebug(javaClass.name, "onNewToken: ${token}")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (message.notification != null) sendNotification(message)
        else LogDebug(javaClass.name, "수신 에러: Notification이 비어있습니다")
    }

    private fun sendNotification(remoteMessage: RemoteMessage){
        val id = 0
        val title = remoteMessage.notification!!.title
        val body = remoteMessage.notification!!.body
        val noti = remoteMessage.data.getValue("route")

        LogDebug(javaClass.name, noti.toString())

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, id, intent, PendingIntent.FLAG_IMMUTABLE)

        val channelId = "Channel ID"
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_HIGH)

        notificationManager.createNotificationChannel(channel)
        notificationManager.notify(id, notificationBuilder.build())
    }


}