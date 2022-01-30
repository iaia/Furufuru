package dev.iaiabot.furufuru.data.local

import android.util.LruCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

internal interface ScreenshotDataSource {
    val screenShotFlow: Flow<String?>

    suspend fun save(fileStr: String)
    suspend fun remove()
    suspend fun load(): String?
}

internal class ScreenshotDataSourceImpl(
    private val cache: LruCache<String, String>
) : ScreenshotDataSource {
    companion object {
        private const val SCREENSHOT_KEY = "screenshot"
    }

    override val screenShotFlow = MutableStateFlow<String?>(null)

    override suspend fun save(fileStr: String) {
        synchronized(cache) {
            cache.put(SCREENSHOT_KEY, fileStr)
            screenShotFlow.tryEmit(fileStr)
        }
    }

    override suspend fun load(): String? {
        return synchronized(cache) {
            cache.get(SCREENSHOT_KEY)
        }
    }

    override suspend fun remove() {
        synchronized(cache) {
            cache.remove(SCREENSHOT_KEY)
            screenShotFlow.tryEmit(null)
        }
    }
}
