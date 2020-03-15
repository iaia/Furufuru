package com.example.furufuru

import android.app.Application
import com.example.feature.Furufuru

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Furufuru.builder(
            this,
            "iaia",
            "Furufuru"
        ).build()
    }
}
