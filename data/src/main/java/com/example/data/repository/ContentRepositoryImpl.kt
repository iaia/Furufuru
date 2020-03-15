package com.example.data.repository

import android.util.Log
import com.example.data.entity.Content
import com.example.data.entity.ContentResponse
import com.example.data.entity.Issue
import com.example.data.remote.github.GithubService

class ContentRepositoryImpl(
    private val owner: String,
    private val repo: String,
    private val service: GithubService
) : ContentRepository {
    override suspend fun post(content: Content, path: String): ContentResponse? {
        try {
            service.postContent(
                owner, repo,
                mapOf(
                    Pair("message", content.message),
                    Pair("content", content.content),
                    Pair("sha", content.sha),
                    Pair("branch", content.branch)
                ),
                path
            ).run {
                Log.d("koko", "koko234")
                // Log.d("koko", "$isSuccessful")
                // Log.d("koko", "${message()}")
                return body()
            }
        } catch (e: Exception) {
            Log.d("koko", e.toString())
        }
        return null
    }
}
