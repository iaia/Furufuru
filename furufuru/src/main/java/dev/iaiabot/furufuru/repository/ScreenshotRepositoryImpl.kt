package dev.iaiabot.furufuru.repository

import dev.iaiabot.furufuru.data.local.ScreenshotDataSource
import kotlinx.coroutines.flow.Flow

internal class ScreenshotRepositoryImpl(
    private val screenshotDataSource: ScreenshotDataSource,
) : ScreenshotRepository {

    override suspend fun save(fileStr: String) {
        screenshotDataSource.save(fileStr)
    }

    override suspend fun remove() {
        screenshotDataSource.remove()
    }

    override suspend fun load(remove: Boolean): String? {
        val file = screenshotDataSource.load()
        if (remove) {
            screenshotDataSource.remove()
        }
        return file
    }

    override suspend fun observe(): Flow<String?> {
        return screenshotDataSource.screenShotFlow
    }
}
