package dev.iaiabot.furufuru.repository

import dev.iaiabot.furufuru.data.local.ScreenshotEntity

internal class ScreenshotRepositoryImpl(
    private val screenshotEntity: ScreenshotEntity
) : ScreenshotRepository {

    override fun save(fileStr: String) {
        screenshotEntity.save(fileStr)
    }

    override fun get(remove: Boolean): String? {
        val screenshot = screenshotEntity.get()
        if (remove) {
            screenshotEntity.remove()
        }
        return screenshot
    }
}
