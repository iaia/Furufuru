package dev.iaiabot.furufuru.feature.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import dev.iaiabot.furufuru.feature.R
import dev.iaiabot.furufuru.feature.ui.issue.IssueActivity

internal object FurufuruNotification {
    enum class Channels(val channelId: String) {
        SENSOR_SERVICE("SENSOR_SERVICE_CHANNEL_ID"),
        BUBBLE("bubble"),
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(notificationManager: NotificationManager) {
        Channels.values().forEach {
            val channelId = it.channelId
            if (notificationManager.getNotificationChannel(channelId) == null) {
                val channel = NotificationChannel(
                    channelId,
                    "Furufuru",
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

    @RequiresApi(Build.VERSION_CODES.Q)
    fun createBubbleNotification(context: Context): Notification {
        val target = IssueActivity.createIntent(context)
        val contentIntent = PendingIntent.getActivity(context, 0, target, 0)
        val bubbleData = Notification.BubbleMetadata
            .Builder()
            //.Builder(bubbleIntent, Icon.createWithResource(context, R.drawable.ic_send))
            .setIcon(Icon.createWithResource(context, R.drawable.ic_send))
            .setIntent(contentIntent)
            .setDesiredHeight(600)
            .setAutoExpandBubble(false)
            .setSuppressNotification(false)
            .build()
        return Notification.Builder(context, Channels.BUBBLE.channelId)
            .setSmallIcon(R.drawable.ic_send)
            .setContentTitle("Furufuru Bubbles")
            .setContentText("Furufuru bubbles")
            .setBubbleMetadata(bubbleData)
            .setContentIntent(contentIntent)
            .setCategory("CATEGORY_ALL")
            .build()
    }

    fun createSensorServiceNotification(context: Context): Notification {
        return NotificationCompat.Builder(
            context,
            Channels.SENSOR_SERVICE.channelId
        ).apply {
            setContentTitle("Furufuru Sensor")
            setContentText("Furufuru sensor service is running")
        }.build()
    }
}
