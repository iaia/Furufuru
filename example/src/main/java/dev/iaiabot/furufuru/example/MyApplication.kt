package dev.iaiabot.furufuru.example

import android.app.Application
import dev.iaiabot.furufuru.Furufuru

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Furufuru.Builder(this)
            .setGithubApiToken(BuildConfig.GITHUB_API_TOKEN)
            .setGithubReposOwner("iaia")
            .setGithubRepository("Furufuru")
            .setLabels("bug", "documentation")
            .build()
    }
}
