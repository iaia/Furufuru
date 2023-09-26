package dev.iaiabot.furufuru.repository

import dev.iaiabot.furufuru.data.entity.ContentImageUrls
import dev.iaiabot.furufuru.data.github.GithubService
import dev.iaiabot.furufuru.data.github.request.Content
import dev.iaiabot.furufuru.util.GithubSettings

internal class ContentRepositoryImpl(
    private val settings: GithubSettings,
    private val service: GithubService,
) : ContentRepository {

    override suspend fun post(content: Content, path: String): ContentImageUrls? {
        service.postContent(
            settings.githubRepositoryOwner,
            settings.githubRepository,
            content,
            path
        ).run {
            if (code() != 201) {
                val errorBody = errorBody()?.string() ?: ""
                val errorMessage = message()
                throw Exception("$errorBody; $errorMessage")
            }
            val contentResult =
                body()?.content ?: throw Exception("${errorBody().toString()}; ${message()}")
            return ContentImageUrls(
                contentResult.htmlUrl,
                contentResult.downloadUrl
            )
        }
    }
}
