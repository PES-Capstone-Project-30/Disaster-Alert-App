package com.jacob.disasteralertapp.common.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jacob.disasteralertapp.MainActivity
import com.jacob.disasteralertapp.R
import com.jacob.disasteralertapp.common.AuthData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShowNotification : FirebaseMessagingService() {
    @Inject
    lateinit var authData: AuthData

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification == null) return

        generateNotification(
            remoteMessage.notification!!.title!!,
            remoteMessage.notification!!.body!!
        )
    }

    private fun getRemoteView(title: String, message: String): RemoteViews =
        RemoteViews("com.jacob.disasteralertapp", R.layout.notification).also {
            it.setTextViewText(R.id.title_notification, title)
            it.setTextViewText(R.id.notification_message, message)
            it.setImageViewResource(R.id.app_logo, R.drawable.ic_map)
        }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun generateNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.ic_map)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setContent(getRemoteView(title, message))
            .build()

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notificationChannel)

        notificationManager.notify(0, notification)
    }

    companion object {
        const val channelId = "notification_channel"
        const val channelName = "com.jacob.disasteralertapp"
    }
}
