package dev.iaiabot.furufuru.feature

import android.app.Application
import android.content.pm.PackageInfo
import dev.iaiabot.furufuru.di.diModules
import dev.iaiabot.furufuru.feature.utils.lifecycle.FurufuruLifecycleCallback
import dev.iaiabot.furufuru.util.FurufuruSettings
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.inject

class Furufuru(
    private val application: Application,
) {

    companion object {
        private var instance: Furufuru? = null

        fun builder(
            application: Application,
            githubApiToken: String,
            githubReposOwner: String,
            githubRepository: String,
            furufuruBranch: String? = null
        ): Furufuru {
            return Furufuru(application).also {
                it.settings.init(
                    githubApiToken,
                    githubReposOwner,
                    githubRepository,
                    furufuruBranch,
                )
                instance = it
            }
        }

        internal fun getAppVersionName() = getInstance()?.getApplicationVersion()

        internal fun getApplicationName() = getInstance()?.getApplicationName()

        private fun getInstance(): Furufuru? {
            return instance
        }
    }

    private val settings: FurufuruSettings by inject(FurufuruSettings::class.java)
    private val applicationLifecycleCallbacks: FurufuruLifecycleCallback =
        FurufuruLifecycleCallback()

    init {
        startKoin {
            androidLogger()
            androidContext(application)
            modules(diModules())
        }
    }

    fun build() {
        application.registerActivityLifecycleCallbacks(applicationLifecycleCallbacks)
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
