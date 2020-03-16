package com.example.data.repository

import com.example.data.entity.Content
import com.example.data.entity.ContentResponse
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
                content,
                /*
                mapOf(
                    Pair("message", content.message),
                    Pair("content", content.content),
                    Pair("sha", content.sha),
                    Pair("branch", content.branch)
                ),
                 */
                path
            ).run {
                return body()
            }
        } catch (e: Exception) {
        }
        return null
    }
}
