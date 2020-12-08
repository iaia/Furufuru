package dev.iaiabot.furufuru.usecase

import android.text.format.DateFormat
import dev.iaiabot.furufuru.data.entity.Content
import dev.iaiabot.furufuru.data.entity.Issue
import dev.iaiabot.furufuru.data.repository.ContentRepository
import dev.iaiabot.furufuru.data.repository.IssueRepository
import dev.iaiabot.furufuru.data.repository.ScreenshotRepository
import dev.iaiabot.furufuru.feature.ui.issue.IssueBodyTemplate
import dev.iaiabot.furufuru.util.FurufuruSettings
import kotlinx.coroutines.delay
import java.util.*

internal class IssueUseCaseImpl(
    private val issueRepository: IssueRepository,
    private val screenshotRepository: ScreenshotRepository,
    private val contentRepository: ContentRepository,
    private val furufuruSettings: FurufuruSettings,
) : IssueUseCase {

    override suspend fun post(title: String, userName: String, body: String) {
        val imageUrls = uploadScreenShot()

        val issue = Issue(
            title,
            IssueBodyTemplate.createBody(
                userName,
                body,
                imageUrls?.imageUrl,
                imageUrls?.fileUrl
            )
        )

        issueRepository.post(issue)
    }

    override suspend fun getScreenShot(): String? {
        repeat(3) { repeatNum ->
            val screenshot = screenshotRepository.get()
            if (screenshot == null) {
                delay(1000L * repeatNum)
                return@repeat
            } else {
                return screenshot
            }
        }
        return null
    }

    override suspend fun uploadScreenShot(): IssueUseCase.ImageUrls? {
        val screenshot = screenshotRepository.get()
        if (screenshot.isNullOrEmpty()) {
            return null
        }
        // FIXME: branchをここで渡したくない
        val content = Content(
            "[ci skip] Upload furufuru image",
            screenshot,
            null,
            furufuruSettings.furufuruBranch
        )
        val result = contentRepository.post(content, generateUploadDestinationPath())

        return if (result == null) {
            null
        } else {
            IssueUseCase.ImageUrls(
                result.content.htmlUrl,
                result.content.downloadUrl
            )
        }
    }

    private fun generateUploadDestinationPath(): String {
        val now = Date()
        val nowString = DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)
        return "$nowString.jpg"
    }
}
