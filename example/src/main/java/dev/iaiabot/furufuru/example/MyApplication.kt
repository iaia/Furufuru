package dev.iaiabot.furufuru.example

import android.app.Application
import dev.iaiabot.furufuru.feature.Furufuru

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Furufuru.Builder(
            this,
        ).settingGithub(
            BuildConfig.GITHUB_API_TOKEN,
            "iaia",
            "Furufuru"
        ).setLabels(
            "bug", "documentation"
        ).build()
    }
}
