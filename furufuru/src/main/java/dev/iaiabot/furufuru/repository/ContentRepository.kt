package dev.iaiabot.furufuru.repository

import dev.iaiabot.furufuru.data.entity.Content
import dev.iaiabot.furufuru.data.entity.ContentImageUrls

internal interface ContentRepository {
    suspend fun post(content: Content, path: String): ContentImageUrls?
}
