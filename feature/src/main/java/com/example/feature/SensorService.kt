package com.example.feature

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.widget.Toast
import kotlin.math.sqrt

class SensorService : Service() {
    private lateinit var sensorManager: SensorManager
    private var access = 0f
    private var accessCurrent = 0f
    private var accessLast = 0f

    companion object {
        const val HELLO = 0
        const val DETECT_SHAKE = 1
        const val ACCEPTING = 2
    }

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
                Toast.makeText(applicationContext, "Shake event detected", Toast.LENGTH_SHORT)
                    .show()
                //sensorManager.unregisterListener(this)
                val message: Message = Message.obtain(null, DETECT_SHAKE, 0, 0)
                messenger.send(message)
            }
        }
    }

    private lateinit var messenger: Messenger

    internal class IncomingHandler : Handler() {
        lateinit var hostMessenger: Messenger
        var processing = false

        override fun handleMessage(message: Message) {
            when (message.what) {
                HELLO -> {
                    hostMessenger = message.replyTo
                }
                DETECT_SHAKE -> {
                    if (!processing) {
                        processing = true
                        hostMessenger.send(Message.obtain())
                    }
                }
                ACCEPTING -> {
                    processing = false
                }
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        sensorManager.registerListener(
            sensorEventListener,
            sensor,
            Sensor.TYPE_ACCELEROMETER,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        messenger = Messenger(IncomingHandler())
        messenger.send(Message.obtain(null, ACCEPTING, 0, 0))

        return messenger.binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        sensorManager.unregisterListener(sensorEventListener)

        return super.onUnbind(intent)
    }
}
