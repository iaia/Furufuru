package dev.iaiabot.furufuru.data.repository

import android.util.LruCache

internal class ScreenshotRepositoryImpl : ScreenshotRepository {
    companion object {
        const val SCREENSHOT_KEY = "screenshot"
    }

    private val cache = LruCache<String, String>(1)

    override fun save(fileStr: String) {
        synchronized(cache) {
            cache.put(SCREENSHOT_KEY, fileStr)
        }
    }

    override fun get(): String? {
        return synchronized(cache) {
            cache.get(SCREENSHOT_KEY)
        }
    }
}
