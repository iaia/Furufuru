package com.example.feature

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Environment
import android.os.Handler
import android.text.format.DateFormat
import android.view.PixelCopy
import android.view.View
import java.io.File
import java.io.FileOutputStream
import java.util.*

class Furufuru(private val activity: Activity) {
    companion object {
        fun openIssue(activity: Activity) {
            Furufuru(activity).apply {
                takeScreenshot().run {
                    openIssue(this)
                }
            }
        }
    }

    private fun openIssue(filePath: String?) {
        IssueActivity.createIntent(activity, filePath).run {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(this)
        }
    }

    private fun takeScreenshot(): String? {
        val now = Date()
        DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)
        val path = activity.getExternalFilesDir(null).toString() + "/" + now + ".jpg"

        return path
    }
}
