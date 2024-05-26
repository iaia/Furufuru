package dev.iaiabot.furufuru

import android.app.Application
import android.content.pm.PackageInfo
import dev.iaiabot.furufuru.di.diModules
import dev.iaiabot.furufuru.feature.utils.lifecycle.FurufuruLifecycleCallback
import dev.iaiabot.furufuru.util.GithubSettings
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.java.KoinJavaComponent.inject

class Furufuru private constructor(
    private val application: Application,
) {

    class Builder(
        private val application: Application
    ) {
        private var githubApiToken: String? = null
        private var githubReposOwner: String? = null
        private var githubRepository: String? = null
        private var furufuruBranch: String? = null
        private var labels: List<String> = emptyList()

        fun setGithubApiToken(githubApiToken: String): Builder {
            this.githubApiToken = githubApiToken
            return this
        }

        fun setGithubReposOwner(githubReposOwner: String): Builder {
            this.githubReposOwner = githubReposOwner
            return this
        }

        fun setGithubRepository(githubRepository: String): Builder {
            this.githubRepository = githubRepository
            return this
        }

        fun setGithubBranch(
            furufuruBranch: String? = null
        ): Builder {
            this.furufuruBranch = furufuruBranch
            return this
        }

        fun setLabels(vararg labels: String): Builder {
            this.labels = labels.map { it }
            return this
        }

        fun build(): Furufuru {
            // TODO: すでにあるinstance破棄して新しく作り直したい
            val instance = getInstance(application) ?: throw Exception()
            instance.githubSettings.init(
                githubApiToken = githubApiToken ?: "",
                githubReposOwner = githubReposOwner ?: "",
                githubRepository = githubRepository ?: "",
                furufuruBranch = furufuruBranch,
            )
            instance.githubSettings.addLabels(labels)
            instance.start()
            return instance
        }
    }

    companion object {
        private var instance: Furufuru? = null

        internal fun getAppVersionName() = getInstance()?.getApplicationVersion()

        internal fun getApplicationName() = getInstance()?.getApplicationName()

        internal fun takeScreenshot() {
            getInstance()?.takeScreenshot()
        }

        private fun getInstance(application: Application? = null): Furufuru? {
            if (instance == null) {
                application?.let {
                    instance = Furufuru(application)
                }
            }
            return instance
        }
    }

    private val githubSettings: GithubSettings by inject(GithubSettings::class.java)
    private val applicationLifecycleCallbacks: FurufuruLifecycleCallback =
        FurufuruLifecycleCallback()

    init {
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(application)
            modules(diModules())
        }
    }

    internal fun start() {
        application.registerActivityLifecycleCallbacks(applicationLifecycleCallbacks)
    }

    fun takeScreenshot() {
        applicationLifecycleCallbacks.takeScreenshot()
    }

    private fun getApplicationName(): String? {
        val applicationInfo = application.applicationInfo
        val stringId = applicationInfo.labelRes
        return if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else application.getString(
            stringId
        )
    }

    private fun getApplicationVersion(): String {
        val pInfo: PackageInfo =
            application.packageManager.getPackageInfo(
                application.packageName, 0
            )
        return pInfo.versionName
    }
}
