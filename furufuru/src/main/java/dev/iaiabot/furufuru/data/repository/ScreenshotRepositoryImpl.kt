package dev.iaiabot.furufuru.data.repository

import android.content.Context

class ScreenshotRepositoryImpl : ScreenshotRepository {
    companion object {
        const val SCREENSHOT_KEY = "screenshot"
    }

    override fun save(context: Context, fileStr: String) {
        val pref = context.getSharedPreferences(
            "furufuru", Context.MODE_PRIVATE
        )
        val editor = pref.edit()
        editor.putString(SCREENSHOT_KEY, fileStr)
        editor.apply()
    }

    override fun get(context: Context): String? {
        val pref = context.getSharedPreferences(
            "furufuru", Context.MODE_PRIVATE
        )
        return pref.getString(SCREENSHOT_KEY, null)
    }
}
