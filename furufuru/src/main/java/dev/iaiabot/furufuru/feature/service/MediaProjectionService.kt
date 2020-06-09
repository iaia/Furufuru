package dev.iaiabot.furufuru.feature.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.PixelFormat
import android.hardware.display.DisplayManager
import android.hardware.display.VirtualDisplay
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.IBinder
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import dev.iaiabot.furufuru.data.repository.ScreenshotRepository
import dev.iaiabot.furufuru.feature.ui.issue.IssueActivity
import org.koin.android.ext.android.inject
import java.io.ByteArrayOutputStream

class MediaProjectionService : Service() {
    companion object {
        private const val ARG_WIDTH = "arg_width"
        private const val ARG_HEIGHT = "arg_height"
        private const val ARG_DENSITY = "arg_density"
        private const val ARG_RESULT_CODE = "arg_result_code"
        private const val ARG_DATA = "arg_data"

        fun createIntent(
            context: Context,
            width: Int,
            height: Int,
            density: Int,
            resultCode: Int,
            data: Intent
        ): Intent {
            return Intent(context, MediaProjectionService::class.java).apply {
                putExtra(ARG_WIDTH, width)
                putExtra(ARG_HEIGHT, height)
                putExtra(ARG_DENSITY, density)
                putExtra(ARG_RESULT_CODE, resultCode)
                putExtra(ARG_DATA, data)
            }
        }
    }

    private var mediaProjectionManager: MediaProjectionManager? = null
    private var mediaProjection: MediaProjection? = null
    private var virtualDisplay: VirtualDisplay? = null
    private var imageReader: ImageReader? = null
    private var width: Int = 0
    private var height: Int = 0
    private var density: Int = 0
    private var resultCode: Int = 0
    private lateinit var data: Intent
    private val screenshotRepository by inject<ScreenshotRepository>()

    override fun onBind(intent: Intent): IBinder? {
        width = intent.getIntExtra(ARG_WIDTH, 0)
        height = width // intent.getIntExtra(ARG_HEIGHT, 0)
        density = intent.getIntExtra(ARG_DENSITY, 0)
        resultCode = intent.getIntExtra(ARG_RESULT_CODE, 0)
        data = intent.getParcelableExtra(ARG_DATA) ?: return null

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startNotification()
        }

        mediaProjectionManager =
            ContextCompat.getSystemService(this, MediaProjectionManager::class.java)
        mediaProjection =
            mediaProjectionManager?.getMediaProjection(resultCode, data)

        imageReader = ImageReader
            .newInstance(width, height, PixelFormat.RGBA_8888, 1)
        imageReader?.setOnImageAvailableListener({
            goIssueActivity()
        }, null)
        virtualDisplay = mediaProjection?.createVirtualDisplay(
            "Capturing Display",
            width, height, density,
            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
            imageReader?.surface, null, null
        )
        return null
    }

    private fun goIssueActivity() {
        val bitmap = getScreenshot() ?: return

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val jpgarr = baos.toByteArray()
        val fileStr = Base64.encodeToString(jpgarr, Base64.NO_WRAP)
        screenshotRepository.save(this, fileStr)

        stopRec()
        startActivity(IssueActivity.createIntent(this).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        })
        stopForeground(true)
        stopSelf()
    }

    private fun getScreenshot(): Bitmap? {
        val image = imageReader?.acquireLatestImage() ?: return null

        image.planes[0].run {
            val bitmap = Bitmap.createBitmap(
                rowStride / pixelStride, height, Bitmap.Config.ARGB_8888)
            bitmap.copyPixelsFromBuffer(buffer)
            image.close()
            return bitmap
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startNotification() {
        val notificationManager = ContextCompat.getSystemService(this, NotificationManager::class.java)!!
        val notificationChannelId = "TAKE_SCREENSHOT_SERVICE_CHANNEL_ID"
        if (notificationManager.getNotificationChannel(notificationChannelId) == null) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    notificationChannelId,
                    "TAKE SCREENSHOT SERVICE CHANNEL",
                    NotificationManager.IMPORTANCE_LOW
                )
            )
        }
        val notification = NotificationCompat.Builder(
            applicationContext,
            notificationChannelId
        ).apply {
            setContentTitle("Taking a screenshot")
            setContentText("Taking a screenshot")
        }.build()
        startForeground(1, notification)
    }

    private fun stopRec() {
        imageReader?.close()
        virtualDisplay?.release()
        mediaProjection?.stop()
    }
}
