package dev.iaiabot.furufuru.feature

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.os.Build
import android.os.Bundle
import dev.iaiabot.furufuru.data.FURUFURU_BRANCH
import dev.iaiabot.furufuru.data.GITHUB_API_TOKEN
import dev.iaiabot.furufuru.data.GITHUB_REPOSITORY
import dev.iaiabot.furufuru.data.GITHUB_REPOSITORY_OWNER
import dev.iaiabot.furufuru.di.apiModule
import dev.iaiabot.furufuru.di.repositoryModule
import dev.iaiabot.furufuru.di.viewModelModule
import dev.iaiabot.furufuru.feature.service.SensorService
import dev.iaiabot.furufuru.feature.ui.issue.IssueActivity
import dev.iaiabot.furufuru.feature.ui.prepare.PrepareActivity
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


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

        fun restartSensorService() {
            getInstance()?.startSensorService()
        }

        fun getApplicationName() = getInstance()?.getApplicationName()
        fun getAppVersionName() = getInstance()?.getApplicationVersion()

        private fun getInstance(): Furufuru? {
            return instance
        }
    }

    private var sensorServiceIntent: Intent? = null

    init {
        startKoin {
            androidLogger()
            androidContext(application)
            modules(
                listOf(
                    viewModelModule,
                    apiModule,
                    repositoryModule
                )
            )
        }
    }

    fun build() {
        application.registerActivityLifecycleCallbacks(applicationLifecycleCallbacks)
    }

    fun getApplicationName(): String? {
        val applicationInfo = application.applicationInfo
        val stringId = applicationInfo.labelRes
        return if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else application.getString(
            stringId
        )
    }

    fun getApplicationVersion(): String {
        val pInfo: PackageInfo =
            application.packageManager.getPackageInfo(
                application.packageName, 0
            )
        return pInfo.versionName
    }

    private fun startSensorService() {
        Intent(application, SensorService::class.java).also { intent ->
            sensorServiceIntent = intent
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                application.startForegroundService(intent)
            } else {
                application.startService(intent)
            }
        }
    }

    private fun stopSensorService() {
        sensorServiceIntent?.let {
            application.stopService(it)
            sensorServiceIntent = null
        }
    }

    private val applicationLifecycleCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityStarted(activity: Activity) {
            if (activity is PrepareActivity || activity is IssueActivity) {
                return
            }
            startSensorService()
        }

        override fun onActivityPaused(activity: Activity) {
            stopSensorService()
        }

        override fun onActivityDestroyed(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        }

        override fun onActivityResumed(activity: Activity) {
        }
    }
}
