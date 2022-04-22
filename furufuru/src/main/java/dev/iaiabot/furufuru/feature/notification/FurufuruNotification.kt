package dev.iaiabot.furufuru.feature.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.IconCompat
import dev.iaiabot.furufuru.R
import dev.iaiabot.furufuru.feature.ui.issue.IssueActivity

internal object FurufuruNotification {
    enum class Channels(val channelId: String, val channelName: String) {
        FURUFURU("_FURUFURU_CHANNEL", "Furufuru"),
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(notificationManager: NotificationManager) {
        Channels.values().forEach { channel ->
            if (notificationManager.getNotificationChannel(channel.channelId) == null) {
                val notificationChannel = NotificationChannel(
                    channel.channelId,
                    channel.channelName,
                    NotificationManager.IMPORTANCE_LOW
                )
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    notificationChannel.setAllowBubbles(true)
                }
                notificationManager.createNotificationChannel(
                    notificationChannel
                )
            }
        }
    }

    fun createSensorNotification(context: Context): Notification {
        val target = IssueActivity.createIntent(context)
        val contentIntent = PendingIntent.getActivity(context, 0, target, PendingIntent.FLAG_IMMUTABLE)
        return NotificationCompat
            .Builder(context, Channels.FURUFURU.channelId)
            .setSmallIcon(R.drawable.ic_send) // TODO: furufuruのアイコン作る
            .setContentTitle("Furufuru is running")
            .setContentIntent(contentIntent) // 二重に設定する?
            .setCategory(Notification.CATEGORY_CALL)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    bubbleMetadata = createBubbleMetaData(context, contentIntent)
                }
            }
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun createBubbleMetaData(context: Context, contentIntent: PendingIntent): NotificationCompat.BubbleMetadata {
        return NotificationCompat.BubbleMetadata
            .Builder()
            .setIcon(IconCompat.createWithResource(context, R.drawable.ic_send))
            .setIntent(contentIntent)
            .setDesiredHeight(600)
            .setAutoExpandBubble(false)
            .setSuppressNotification(false)
            .build()
    }
}
