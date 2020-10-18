package dev.iaiabot.furufuru.feature.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi

object NotificationChannel {
    enum class Channels(val channelId: String) {
        SENSOR_SERVICE("SENSOR_SERVICE_CHANNEL_ID")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(notificationManager: NotificationManager) {
        Channels.values().forEach {
            val channelId = it.channelId
            if (notificationManager.getNotificationChannel(channelId) == null) {
                val channel = NotificationChannel(
                    channelId,
                    "SENSOR SERVICE CHANNEL",
                    NotificationManager.IMPORTANCE_LOW
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    channel.setAllowBubbles(true)
                }
                notificationManager.createNotificationChannel(
                    channel
                )
            }
        }
    }
}
