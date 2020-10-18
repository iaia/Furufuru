package dev.iaiabot.furufuru.feature.service

import android.app.*
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.drawable.Icon
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import dev.iaiabot.furufuru.data.SENSOR_NOTIFICATION_CHANNEL_ID
import dev.iaiabot.furufuru.feature.Furufuru
import dev.iaiabot.furufuru.feature.R
import dev.iaiabot.furufuru.feature.notification.NotificationChannel
import dev.iaiabot.furufuru.feature.ui.issue.IssueActivity
import kotlin.math.sqrt

class SensorService : Service() {
    companion object {
        const val NOTIFICATION_ID = 1001
    }

    private var sensorManager: SensorManager? = null
    private var access = 0f
    private var accessCurrent = 0f
    private var accessLast = 0f
    private var detected = false
    private val binder = LocalBinder()

    private val sensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }

        override fun onSensorChanged(event: SensorEvent) {
            val x: Float = event.values[0]
            val y: Float = event.values[1]
            val z: Float = event.values[2]

            accessLast = accessCurrent
            accessCurrent = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = accessCurrent - accessLast
            access = access * 0.9f + delta
            if (access > 12) {
                if (detected) {
                    return
                }
                detected = true
                openIssue()
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()

        sensorManager?.unregisterListener(sensorEventListener)
        stopForeground(true)
    }

    private fun startSensorManager() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? =
            sensorManager?.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) ?: return
        sensorManager?.registerListener(
            sensorEventListener,
            sensor,
            Sensor.TYPE_ACCELEROMETER,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startNotification() {
        val notificationManager =
            ContextCompat.getSystemService(this, NotificationManager::class.java)!!
        NotificationChannel.createNotificationChannel(notificationManager)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val context = applicationContext
            val target = IssueActivity.createIntent(context)
            // TODO: flags, option を調査
            val bubbleIntent = PendingIntent.getActivity(context, 0, target, 0)
            val bubbleData = Notification.BubbleMetadata.Builder()
                .setIcon(Icon.createWithResource(context, R.drawable.ic_send))
                .setDesiredHeight(600)
                .setIntent(bubbleIntent)
                .setAutoExpandBubble(true)
                // .setSuppressNotification(true)
                .build()
            val chatBot = Person.Builder()
                .setBot(true)
                .setName("FurufuruBot")
                .setImportant(true)
                .build()
            val n = Notification.Builder(context, SENSOR_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_send)
                //.setContentIntent(contentIntent)
                .setBubbleMetadata(bubbleData)
                .setCategory("CATEGORY_ALL")
                .addPerson(chatBot)
                .build()
            startForeground(NOTIFICATION_ID, n)
        } else {
            val notification = NotificationCompat.Builder(
                applicationContext,
                NotificationChannel.Channels.SENSOR_SERVICE.channelId
            ).apply {
                setContentTitle("Furufuru Sensor")
                setContentText("Furufuru sensor service is running")
            }.build()

            startForeground(NOTIFICATION_ID, notification)
        }
    }

    private fun openIssue() {
        Furufuru.takeScreenshot()
        startActivity(IssueActivity.createIntent(this).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    fun attach() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startNotification()
        }
        startSensorManager()
    }

    fun detach() {
        stopForeground(true)
    }

    inner class LocalBinder : Binder() {
        fun getService() = this@SensorService
    }

    class Connection : ServiceConnection {
        var service: SensorService? = null

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            this.service = (service as? LocalBinder)?.getService()
            this.service?.attach()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            this.service?.detach()
            this.service = null
        }
    }
}
