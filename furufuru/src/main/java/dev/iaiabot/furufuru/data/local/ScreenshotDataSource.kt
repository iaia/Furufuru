package dev.iaiabot.furufuru.data.local

import android.util.LruCache
import dev.iaiabot.furufuru.data.entity.ScreenShot
import kotlinx.coroutines.flow.MutableStateFlow

internal class ScreenshotDataSource(
    private val cache: LruCache<String, String>
) : ScreenShot {
    companion object {
        private const val SCREENSHOT_KEY = "screenshot"
    }

    override val screenShotFlow = MutableStateFlow<String?>(null)

    override suspend fun save(fileStr: String) {
        synchronized(cache) {
            screenShotFlow.tryEmit(fileStr)
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
