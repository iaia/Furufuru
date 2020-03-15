package com.example.data.repository

import android.util.Log
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
                owner, repo,
                mapOf(
                    Pair("title", issue.title), Pair("body", issue.body)
                )
            ).run {
                Log.d("koko", "$isSuccessful")
                Log.d("koko", this.message())
            }
        } catch (e: Exception) {
            Log.d("koko", e.toString())
        }
    }
}
