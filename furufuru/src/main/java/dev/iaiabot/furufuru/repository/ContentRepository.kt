package dev.iaiabot.furufuru.repository

import dev.iaiabot.furufuru.data.entity.ContentImageUrls
import dev.iaiabot.furufuru.data.github.request.Content

internal interface ContentRepository {
    suspend fun post(content: Content, path: String): ContentImageUrls?
}
