package dev.iaiabot.furufuru.data.local

import android.util.LruCache
import dev.iaiabot.furufuru.data.entity.ScreenShot

internal class ScreenshotDataSource(
    private val cache: LruCache<String, String>
) : ScreenShot {
    companion object {
        private const val SCREENSHOT_KEY = "screenshot"
    }

    override fun save(fileStr: String) {
        synchronized(cache) {
            cache.put(SCREENSHOT_KEY, fileStr)
        }
    }

    override fun load(): String? {
        return synchronized(cache) {
            cache.get(SCREENSHOT_KEY)
        }
    }

    override fun remove() {
        synchronized(cache) {
            cache.remove(SCREENSHOT_KEY)
        }
    }
}