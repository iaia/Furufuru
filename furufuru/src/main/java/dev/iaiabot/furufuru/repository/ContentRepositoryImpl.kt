package dev.iaiabot.furufuru.repository

import android.util.Log
import dev.iaiabot.furufuru.data.entity.Content
import dev.iaiabot.furufuru.data.entity.ContentImageUrls
import dev.iaiabot.furufuru.data.github.GithubService
import dev.iaiabot.furufuru.util.FurufuruSettings
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.properties.Properties
import kotlinx.serialization.properties.encodeToMap

internal class ContentRepositoryImpl(
    private val settings: FurufuruSettings,
    private val service: GithubService,
) : ContentRepository {

    @ExperimentalSerializationApi
    override suspend fun post(content: Content, path: String): ContentImageUrls? {
        try {
            service.postContent(
                settings.githubRepositoryOwner,
                settings.githubRepository,
                Properties.encodeToMap(content).mapNotNull {
                    Pair(it.key, it.value as String)
                }.toMap(),
                path
            ).run {
                val contentResult = body()?.content ?: return null
                return ContentImageUrls(
                    contentResult.htmlUrl,
                    contentResult.downloadUrl
                )
            }
        } catch (e: Exception) {
            Log.d("Furufuru Content", e.message ?: "error message is null")
        }
        return null
    }
}
