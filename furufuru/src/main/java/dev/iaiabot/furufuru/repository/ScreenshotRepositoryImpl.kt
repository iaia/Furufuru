package dev.iaiabot.furufuru.repository

import dev.iaiabot.furufuru.data.entity.ScreenShot

internal class ScreenshotRepositoryImpl(
    private val screenshot: ScreenShot
) : ScreenshotRepository {

    override fun save(fileStr: String) {
        screenshot.save(fileStr)
    }

    override fun load(remove: Boolean): String? {
        val file = screenshot.load()
        if (remove) {
            screenshot.remove()
        }
        return file
    }
}
