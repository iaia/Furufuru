package com.example.furufuru

import android.app.Activity
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.feature.Furufuru
import com.example.feature.SensorService
import com.example.feature.SensorService.Companion.HELLO

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Intent(this, SensorService::class.java).also { intent ->
            bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(serviceConnection)
    }


    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        private var messenger: Messenger = Messenger(ResponseHandler(this@MainActivity))
        private var serviceMessenger: Messenger? = null

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            serviceMessenger = Messenger(service)
            val helloMessage = Message.obtain(null, HELLO, 0, 0).apply {
                replyTo = messenger
            }
            serviceMessenger?.send(helloMessage)
        }
        override fun onServiceDisconnected(name: ComponentName?) {
            serviceMessenger = null
        }
    }

    private class ResponseHandler(
        val activity: Activity
    ) : Handler() {
        override fun handleMessage(msg: Message) {
            Furufuru.openIssue(activity)
        }
    }
}
