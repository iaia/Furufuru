package com.example.feature

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Environment
import android.os.IBinder
import android.text.format.DateFormat
import android.view.View
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.math.sqrt

class SensorService: Service() {
    private lateinit var sensorManager: SensorManager
    private var access = 0f
    private var accessCurrent = 0f
    private var accessLast = 0f

    private val sensorEventListener = object: SensorEventListener {
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
                Toast.makeText(applicationContext, "Shake event detected", Toast.LENGTH_SHORT).show()
                sensorManager.unregisterListener(this)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        sensorManager.registerListener(sensorEventListener, sensor, Sensor.TYPE_ACCELEROMETER, SensorManager.SENSOR_DELAY_NORMAL)

        return null
    }

    override fun onUnbind(intent: Intent?): Boolean {
        sensorManager.unregisterListener(sensorEventListener)

        return super.onUnbind(intent)
    }
}
