package dev.iaiabot.furufuru.feature.utils.lifecycle

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.view.View
import dev.iaiabot.furufuru.feature.service.SensorService
import dev.iaiabot.furufuru.feature.ui.issue.IssueActivity
import dev.iaiabot.furufuru.feature.utils.screenshot.ScreenShotter
import org.koin.java.KoinJavaComponent.inject

internal class FurufuruLifecycleCallback : Application.ActivityLifecycleCallbacks {
    private var sensorServiceConnection = SensorService.Connection()
    private val screenShotter: ScreenShotter by inject(ScreenShotter::class.java)

    private var currentActivity: Activity? = null

    fun takeScreenshot() {
        takeScreenshot(currentActivity)
    }

    private fun takeScreenshot(activity: Activity?) {
        activity ?: return
        screenShotter.takeScreenshot(
            activity.window,
            activity.window.decorView.findViewById<View>(android.R.id.content).rootView
        )
    }

    override fun onActivityResumed(activity: Activity) {
        if (activity is IssueActivity) {
            return
        }
        bindSensorService(activity)
        currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {
        if (activity is IssueActivity) {
            return
        }
        takeScreenshot(activity)
        unbindSensorService(activity)
        currentActivity = null
    }

    override fun onActivityDestroyed(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    private fun bindSensorService(activity: Activity) {
        Intent(activity, SensorService::class.java).also { intent ->
            activity.bindService(intent, sensorServiceConnection, Service.BIND_AUTO_CREATE)
        }
    }

    private fun unbindSensorService(activity: Activity) {
        activity.unbindService(sensorServiceConnection)
    }
}
