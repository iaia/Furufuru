package dev.iaiabot.furufuru.repository

import dev.iaiabot.furufuru.data.local.ScreenshotDataSource

internal class ScreenshotRepositoryImpl(
    private val screenshotDataSource: ScreenshotDataSource,
) : ScreenshotRepository {
    override val screenShotFlow = screenshotDataSource.screenShotFlow

    override suspend fun save(fileStr: String) {
        screenshotDataSource.save(fileStr)
    }

    override fun remove() {
        screenshotDataSource.remove()
    }

    /*
    override fun load(remove: Boolean): String? {
        val file = screenshot.load()
        if (remove) {
            screenshot.remove()
        }
        return file
    }
     */
}
