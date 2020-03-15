package com.example.feature.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.IBinder
import com.example.feature.Furufuru
import kotlin.math.sqrt

class SensorService : Service() {
    private lateinit var sensorManager: SensorManager
    private var access = 0f
    private var accessCurrent = 0f
    private var accessLast = 0f

    companion object {
        private var detected = false

        fun startSensorEvent() {
            detected = false
        }
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
                if (detected) {
                    return
                }
                detected = true
                // Toast.makeText(applicationContext, "Shake event detected", Toast.LENGTH_SHORT).show()
                Furufuru.openIssue(this@SensorService.applicationContext)
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

        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        sensorManager.unregisterListener(sensorEventListener)

        return super.onUnbind(intent)
    }
}
