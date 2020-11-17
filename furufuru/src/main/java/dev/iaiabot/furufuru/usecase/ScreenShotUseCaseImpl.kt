package dev.iaiabot.furufuru.usecase

import android.text.format.DateFormat
import dev.iaiabot.furufuru.data.entity.Content
import dev.iaiabot.furufuru.data.repository.ContentRepository
import dev.iaiabot.furufuru.data.repository.ScreenshotRepository
import dev.iaiabot.furufuru.util.FurufuruSettings
import java.util.*

internal class ScreenShotUseCaseImpl(
    private val screenshotRepository: ScreenshotRepository,
    private val contentRepository: ContentRepository,
    private val furufuruSettings: FurufuruSettings,
) : ScreenShotUseCase {

    override suspend fun uploadScreenShot() {
        val screenshot = screenshotRepository.get() ?: return
        // FIXME: branchをここで渡したくない
        val content = Content(
            "[ci skip] Upload furufuru image",
            screenshot,
            null,
            furufuruSettings.furufuruBranch
        )
        contentRepository.post(content, generateUploadDestinationPath())
    }

    private fun generateUploadDestinationPath(): String {
        val now = Date()
        val nowString = DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)
        return "$nowString.jpg"
    }
}
