package dev.iaiabot.furufuru.data.repository

import android.util.Log
import dev.iaiabot.furufuru.data.TAG
import dev.iaiabot.furufuru.data.entity.Issue
import dev.iaiabot.furufuru.data.remote.github.GithubService
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Properties

class IssueRepositoryImpl(
    private val owner: String,
    private val repo: String,
    private val service: GithubService
) : IssueRepository {
    @ImplicitReflectionSerializer
    override suspend fun post(issue: Issue) {
        try {
            service.postIssue(
                owner, repo,
                Properties.storeNullable(issue).mapNotNull {
                    if (it.value == null) {
                        null
                    } else {
                        Pair(it.key, it.value as String)
                    }
                }.toMap()
            )
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
    }
}
