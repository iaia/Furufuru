package dev.iaiabot.furufuru.repository

import dev.iaiabot.furufuru.data.github.GithubService
import dev.iaiabot.furufuru.data.github.request.Issue
import dev.iaiabot.furufuru.util.GithubSettings
import kotlinx.serialization.ExperimentalSerializationApi

internal class IssueRepositoryImpl(
    private val settings: GithubSettings,
    private val service: GithubService,
) : IssueRepository {

    @ExperimentalSerializationApi
    override suspend fun post(issue: Issue) {
        service.postIssue(
            settings.githubRepositoryOwner,
            settings.githubRepository,
            issue,
        )
    }
}
