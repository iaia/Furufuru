package com.example.furufuru

import android.app.Activity
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import com.example.feature.Furufuru
import com.example.feature.SensorService
import com.example.feature.SensorService.Companion.ACCEPTING
import com.example.feature.SensorService.Companion.HELLO

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        Intent(this, SensorService::class.java).also { intent ->
            bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE)
        }

        serviceMessenger?.send(Message.obtain(null, ACCEPTING, 0, 0))
    }

    override fun onStop() {
        super.onStop()
        unbindService(serviceConnection)
    }

    private var serviceMessenger: Messenger? = null

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        private var messenger: Messenger = Messenger(ResponseHandler(this@MainActivity))

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
        override fun handleMessage(message: Message) {
            Furufuru.openIssue(activity)
        }
    }
}
