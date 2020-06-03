package dev.iaiabot.furufuru.feature

import android.app.Application
import android.content.Context
import android.content.Intent
import dev.iaiabot.furufuru.data.FURUFURU_BRANCH
import dev.iaiabot.furufuru.data.GITHUB_API_TOKEN
import dev.iaiabot.furufuru.data.GITHUB_REPOSITORY
import dev.iaiabot.furufuru.data.GITHUB_REPOSITORY_OWNER
import dev.iaiabot.furufuru.feature.service.SensorService
import dev.iaiabot.furufuru.feature.ui.prepare.PrepareActivity


class Furufuru(private val application: Application) {
    companion object {
        private const val DEFAULT_FURUFURU_BRANCH = "furufuru-image-branch"
        private var instance: Furufuru? = null

        fun builder(
            application: Application,
            githubApiToken: String,
            githubReposOwner: String,
            githubRepository: String,
            furufuruBranch: String? = null
        ): Furufuru {
            GITHUB_API_TOKEN = githubApiToken
            GITHUB_REPOSITORY_OWNER = githubReposOwner
            GITHUB_REPOSITORY = githubRepository
            FURUFURU_BRANCH = furufuruBranch ?: DEFAULT_FURUFURU_BRANCH
            return Furufuru(application).also {
                instance = it
            }
        }

        fun openIssue(context: Context) {
            PrepareActivity.createIntent(context).run {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(this)
            }
        }

        fun getInstance(): Furufuru? {
            return instance
        }
    }

    fun build() {
        startSensorService()
    }

    fun startSensorService() {
        Intent(application, SensorService::class.java).also { intent ->
            application.startService(intent)
        }
    }
}
