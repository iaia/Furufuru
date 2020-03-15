package com.example.data.repository

import com.example.data.remote.github.GithubService

class IssueRepositoryImpl(
    private val service: GithubService
) : IssueRepository {
    override suspend fun post() {
        service.postIssue().body().apply {
        }
    }
}
