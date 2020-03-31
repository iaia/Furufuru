package dev.iaiabot.furufuru.data.repository

import android.util.Log
import dev.iaiabot.furufuru.data.entity.Content
import dev.iaiabot.furufuru.data.entity.ContentResponse
import dev.iaiabot.furufuru.data.remote.github.GithubService
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Mapper

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
                Mapper.mapNullable(content).mapNotNull {
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
            Log.d("koko", e.message)
        }
        return null
    }
}
