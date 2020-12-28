package dev.iaiabot.furufuru.usecase

import dev.iaiabot.furufuru.data.entity.ContentImageUrls
import dev.iaiabot.furufuru.data.github.request.Content
import dev.iaiabot.furufuru.data.github.request.Issue
import dev.iaiabot.furufuru.feature.ui.issue.IssueBodyTemplate
import dev.iaiabot.furufuru.repository.ContentRepository
import dev.iaiabot.furufuru.repository.IssueRepository
import dev.iaiabot.furufuru.repository.ScreenshotRepository
import dev.iaiabot.furufuru.util.FurufuruSettings
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
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

    override suspend fun getScreenShot(retryNum: Int): String? {
        repeat(retryNum) { repeatNum ->
            val screenshot = screenshotRepository.load()
            if (screenshot == null) {
                delay(1000L * repeatNum)
                return@repeat
            } else {
                return screenshot
            }
        }
        return null
    }

    private suspend fun uploadScreenShot(): ContentImageUrls? {
        val screenshot = screenshotRepository.load(remove = true)
        if (screenshot.isNullOrEmpty()) {
            return null
        }
        val content = Content(
            content = screenshot,
            branch = furufuruSettings.furufuruBranch
        )
        return contentRepository.post(content, generateUploadDestinationPath())
    }

    private fun generateUploadDestinationPath(): String {
        val now = Date()
        val nowString = SimpleDateFormat("yyyy-MM-dd_hh:mm:ss", Locale.getDefault()).format(now)
        return "$nowString.jpg"
    }
}
