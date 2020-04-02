package dev.iaiabot.furufuru.data.repository

import android.util.Log
import dev.iaiabot.furufuru.data.entity.Content
import dev.iaiabot.furufuru.data.entity.ContentResponse
import dev.iaiabot.furufuru.data.remote.github.GithubService
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Properties

class ContentRepositoryImpl(
    private val owner: String,
    private val repo: String,
    private val service: GithubService
) : ContentRepository {
    @ImplicitReflectionSerializer
    override suspend fun post(content: Content, path: String): ContentResponse? {
        try {
            service.postContent(
                owner, repo,
                Properties.storeNullable(content).mapNotNull {
                    if (it.value == null) {
                        null
                    } else {
                        Pair(it.key, it.value as String)
                    }
                }.toMap()
                ,
                path
            ).run {
                return body()
            }
        } catch (e: Exception) {
            Log.d("Furufuru", e.message)
        }
        return null
    }
}
