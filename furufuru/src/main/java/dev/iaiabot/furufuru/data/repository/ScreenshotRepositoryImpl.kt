package dev.iaiabot.furufuru.data.repository

import android.util.LruCache

internal class ScreenshotRepositoryImpl(
    private val cache: LruCache<String, String>
) : ScreenshotRepository {
    companion object {
        private const val SCREENSHOT_KEY = "screenshot"
    }


    override fun save(fileStr: String) {
        synchronized(cache) {
            cache.put(SCREENSHOT_KEY, fileStr)
        }
    }

    override fun get(remove: Boolean): String? {
        return synchronized(cache) {
            val file = cache.get(SCREENSHOT_KEY)
            if (remove) {
                cache.remove(SCREENSHOT_KEY)
            }
            file
        }
    }
}
