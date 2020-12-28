package dev.iaiabot.furufuru.repository

import dev.iaiabot.furufuru.data.entity.ScreenShot

internal class ScreenshotRepositoryImpl(
    private val screenshot: ScreenShot
) : ScreenshotRepository {

    override fun save(fileStr: String) {
        screenshot.save(fileStr)
    }

    override fun get(remove: Boolean): String? {
        val file = screenshot.get()
        if (remove) {
            screenshot.remove()
        }
        return file
    }
}
