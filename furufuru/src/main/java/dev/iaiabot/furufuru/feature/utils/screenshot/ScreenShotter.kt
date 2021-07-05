package dev.iaiabot.furufuru.feature.utils.screenshot

import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.util.Base64
import android.view.PixelCopy
import android.view.View
import android.view.Window
import androidx.annotation.RequiresApi
import dev.iaiabot.furufuru.repository.ScreenshotRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal class ScreenShotter(
    private val screenshotRepository: ScreenshotRepository
) : CoroutineScope {
    private var screenShotting = false
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + Job()

    private var job: Job? = null

    fun takeScreenshot(window: Window, view: View) {
        if (screenShotting) {
            return
        }
        screenShotting = true
        job?.cancel()
        job = launch(Dispatchers.Main) {
            try {
                val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    getBitmapFromView(window, view)
                } else {
                    getBitmapFromView(view)
                }
                if (bitmap != null) {
                    saveScreenshot(bitmap)
                }
            } catch (e: Exception) {
            } finally {
                screenShotting = false
            }
        }
    }

    private suspend fun saveScreenshot(bitmap: Bitmap) {
        try {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val jpgarr = baos.toByteArray()
            val fileStr = Base64.encodeToString(jpgarr, Base64.NO_WRAP)
            screenshotRepository.save(fileStr)
        } catch (e: Exception) {
        }
    }

    @Suppress("DEPRECATION")
    private suspend fun getBitmapFromView(view: View): Bitmap? {
        return suspendCoroutine { continuation ->
            try {
                val screenView = view.rootView
                screenView.isDrawingCacheEnabled = true
                val bitmap = Bitmap.createBitmap(screenView.drawingCache)
                screenView.isDrawingCacheEnabled = false
                continuation.resume(bitmap)
            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getBitmapFromView(window: Window, view: View): Bitmap? {
        return suspendCoroutine { continuation ->
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val locationOfViewInWindow = IntArray(2)
            view.getLocationInWindow(locationOfViewInWindow)
            try {
                PixelCopy.request(
                    window,
                    Rect(
                        locationOfViewInWindow[0],
                        locationOfViewInWindow[1],
                        locationOfViewInWindow[0] + view.width,
                        locationOfViewInWindow[1] + view.height
                    ),
                    bitmap,
                    { copyResult ->
                        if (copyResult == PixelCopy.SUCCESS) {
                            continuation.resume(bitmap)
                        } else {
                            continuation.resume(null)
                        }
                    },
                    Handler()
                )
            } catch (e: IllegalArgumentException) {
                continuation.resumeWithException(e)
            }
        }
    }
}
