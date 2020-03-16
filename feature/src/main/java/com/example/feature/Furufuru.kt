package com.example.feature

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.text.format.DateFormat
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.util.*


class Furufuru(private val activity: Activity) {
    companion object {
        fun openIssue(activity: Activity) {
            Furufuru(activity).apply {
                takeScreenshot().run {
                    openIssue(this.first, this.second)
                }
            }
        }
    }

    private fun openIssue(filePath: String?, fileStr: String?) {
        IssueActivity.createIntent(activity, filePath, fileStr).run {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(this)
        }
    }

    private fun takeScreenshot(): Pair<String?, String?> {
        val now = Date()
        DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)
        val path = activity.getExternalFilesDir(null).toString() + "/" + now + ".jpg"
        val v = activity.window.decorView.rootView
        v.setDrawingCacheEnabled(true)
        v.buildDrawingCache(true)
        val b: Bitmap = Bitmap.createBitmap(v.getDrawingCache())
        v.setDrawingCacheEnabled(false)

        val baos = ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        val jpgarr = baos.toByteArray()
        val fileStr = Base64.encodeToString(jpgarr, Base64.NO_WRAP);

        val out = FileOutputStream(path)
        b.compress(Bitmap.CompressFormat.JPEG, 90, out)
        out.flush()

        return Pair(path, fileStr)
    }
}
