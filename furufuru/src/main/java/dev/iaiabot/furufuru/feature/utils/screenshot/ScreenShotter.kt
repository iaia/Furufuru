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
import dev.iaiabot.furufuru.data.repository.ScreenshotRepository
import java.io.ByteArrayOutputStream

internal class ScreenShotter(
    private val screenshotRepository: ScreenshotRepository
) {
    fun takeScreenshot(window: Window, view: View) {
        val callback = { bitmap: Bitmap? ->
            if (bitmap != null) {
                saveScreenshot(bitmap)
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getBitmapFromView(window, view, callback)
        } else {
            getBitmapFromView(view, callback)
        }
    }

    private fun saveScreenshot(bitmap: Bitmap) {
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
    private fun getBitmapFromView(view: View, callback: (Bitmap?) -> Unit) {
        val screenView = view.rootView
        screenView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(screenView.drawingCache)
        screenView.isDrawingCacheEnabled = false
        callback.invoke(bitmap)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getBitmapFromView(window: Window, view: View, callback: (Bitmap?) -> Unit) {
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
                        callback.invoke(bitmap)
                    } else {
                        callback.invoke(null)
                    }
                },
                Handler()
            )
        } catch (e: IllegalArgumentException) {
            callback.invoke(null)
        }
    }
}
