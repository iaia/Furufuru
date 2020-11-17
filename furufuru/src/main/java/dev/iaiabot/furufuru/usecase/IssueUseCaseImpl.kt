package dev.iaiabot.furufuru.usecase

import dev.iaiabot.furufuru.data.entity.Issue
import dev.iaiabot.furufuru.data.repository.IssueRepository

internal class IssueUseCaseImpl(
    private val issueRepository: IssueRepository
) : IssueUseCase {

    override suspend fun post(issue: Issue) {
        issueRepository.post(issue)
    }
}
