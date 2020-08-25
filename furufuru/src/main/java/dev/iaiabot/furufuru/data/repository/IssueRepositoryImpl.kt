package dev.iaiabot.furufuru.data.repository

import android.util.Log
import dev.iaiabot.furufuru.data.TAG
import dev.iaiabot.furufuru.data.entity.Issue
import dev.iaiabot.furufuru.data.github.GithubService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.encodeToMap

class IssueRepositoryImpl(
    private val owner: String,
    private val repo: String,
    private val service: GithubService
) : IssueRepository {
    @ExperimentalSerializationApi
    override suspend fun post(issue: Issue) {
        try {
            service.postIssue(
                owner, repo,
                Properties.encodeToMap(issue).mapNotNull {
                    Pair(it.key, it.value as String)
                }.toMap()
            )
        } catch (e: Exception) {
            Log.d(TAG, e.message ?: "error message is null")
        }
    }
}
