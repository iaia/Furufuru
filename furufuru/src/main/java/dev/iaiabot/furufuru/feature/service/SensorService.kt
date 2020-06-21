package dev.iaiabot.furufuru.feature.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import dev.iaiabot.furufuru.feature.Furufuru
import dev.iaiabot.furufuru.feature.ui.issue.IssueActivity
import kotlin.math.sqrt

class SensorService : Service() {
    private lateinit var sensorManager: SensorManager
    private var access = 0f
    private var accessCurrent = 0f
    private var accessLast = 0f
    private var detected = false

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
                stopSelf()
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startNotification()
        }
        startSensorManager()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()

        sensorManager.unregisterListener(sensorEventListener)
        stopForeground(true)
    }

    private fun startSensorManager() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        sensorManager.registerListener(
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
        val notificationChannelId = "SENSOR_SERVICE_CHANNEL_ID"
        if (notificationManager.getNotificationChannel(notificationChannelId) == null) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    notificationChannelId,
                    "SENSOR SERVICE CHANNEL",
                    NotificationManager.IMPORTANCE_LOW
                )
            )
        }
        val notification = NotificationCompat.Builder(
            applicationContext,
            notificationChannelId
        ).apply {
            setContentTitle("Furufuru Sensor")
            setContentText("Furufuru sensor service is running")
        }.build()
        startForeground(1, notification)
    }

    private fun openIssue() {
        Furufuru.takeScreenshot()
        startActivity(IssueActivity.createIntent(this).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}
