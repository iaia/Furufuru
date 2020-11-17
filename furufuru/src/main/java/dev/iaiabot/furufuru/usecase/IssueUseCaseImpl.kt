package dev.iaiabot.furufuru.usecase

import dev.iaiabot.furufuru.data.entity.Issue
import dev.iaiabot.furufuru.data.repository.IssueRepository
import dev.iaiabot.furufuru.feature.ui.issue.IssueBodyTemplate

internal class IssueUseCaseImpl(
    private val issueRepository: IssueRepository,
    private val screenShotUseCase: ScreenShotUseCase
) : IssueUseCase {

    override suspend fun post(title: String, userName: String, body: String) {
        val imageUrls = screenShotUseCase.uploadScreenShot()

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
}
