package com.example.data.repository

import com.example.data.entity.Issue
import com.example.data.remote.github.GithubService

class IssueRepositoryImpl(
    private val owner: String,
    private val repo: String,
    private val service: GithubService
) : IssueRepository {
    override suspend fun post(issue: Issue) {
        try {
            service.postIssue(
                owner, repo, issue
                /*
                mapOf(
                    Pair("title", issue.title), Pair("body", issue.body)
                )
                 */
            )
        } catch (e: Exception) {
        }
    }
}
