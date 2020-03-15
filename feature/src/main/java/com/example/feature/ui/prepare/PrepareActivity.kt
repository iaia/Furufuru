package com.example.feature.ui.prepare

import android.app.Activity
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.feature.R
import com.example.feature.service.MediaProjectionService
import com.example.feature.service.SensorService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class PrepareActivity : AppCompatActivity() {
    companion object {
        const val MEDIA_PROJECTION_REQUEST = 1

        fun createIntent(
            context: Context
        ) = Intent(context, PrepareActivity::class.java).apply {
        }
    }

    private var bound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prepare)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        startMediaProjection()
    }

    private fun startMediaProjection() {
        val mediaProjectionManager =
            ContextCompat.getSystemService(this, MediaProjectionManager::class.java)

        val intent = mediaProjectionManager?.createScreenCaptureIntent()
        startActivityForResult(
            intent,
            MEDIA_PROJECTION_REQUEST
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == MEDIA_PROJECTION_REQUEST &&
            resultCode == Activity.RESULT_OK &&
            data != null) {
            val metrics = resources.displayMetrics
            val width = metrics.widthPixels
            val height = metrics.heightPixels
            val density = metrics.densityDpi

            val intent =
                MediaProjectionService.createIntent(
                    this, width, height, density, resultCode, data
                )
            GlobalScope.launch(Dispatchers.Default) {
                delay(1 * 1000)

                bound = true
                bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE)
            }
        } else {
            SensorService.startSensorEvent()
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        if (bound) {
            unbindService(serviceConnection)
        }
    }

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) { }
        override fun onServiceDisconnected(name: ComponentName?) { }
    }
}
