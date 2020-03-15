package com.example.data.repository

import android.util.Log
import com.example.data.entity.Issue
import com.example.data.remote.github.GithubService
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Mapper

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
                Mapper.mapNullable(issue).mapNotNull {
                    if (it.value == null) {
                        null
                    } else {
                        Pair(it.key, it.value as String)
                    }
                }.toMap()
            )
        } catch (e: Exception) {
            Log.d("koko", e.message)
        }
    }
}
