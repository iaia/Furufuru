package dev.iaiabot.furufuru.repository

import dev.iaiabot.furufuru.data.github.GithubService
import dev.iaiabot.furufuru.data.github.request.Issue
import dev.iaiabot.furufuru.util.FurufuruSettings
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.encodeToMap

internal class IssueRepositoryImpl(
    private val settings: FurufuruSettings,
    private val service: GithubService,
) : IssueRepository {

    @ExperimentalSerializationApi
    override suspend fun post(issue: Issue) {
        service.postIssue(
            settings.githubRepositoryOwner,
            settings.githubRepository,
            Properties.encodeToMap(issue).mapNotNull {
                Pair(it.key, it.value as String)
            }.toMap()
        )
    }
}
