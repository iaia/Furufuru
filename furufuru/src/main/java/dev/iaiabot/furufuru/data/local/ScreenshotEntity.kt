package dev.iaiabot.furufuru.data.local

import android.util.LruCache

internal class ScreenshotEntity(
    private val cache: LruCache<String, String>
) {
    companion object {
        private const val SCREENSHOT_KEY = "screenshot"
    }

    fun save(fileStr: String) {
        synchronized(cache) {
            cache.put(SCREENSHOT_KEY, fileStr)
        }
    }

    fun get(): String? {
        return synchronized(cache) {
            cache.get(SCREENSHOT_KEY)
        }
    }

    fun remove() {
        synchronized(cache) {
            cache.remove(SCREENSHOT_KEY)
        }
    }
}