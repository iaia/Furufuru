package dev.iaiabot.furufuru.data.repository

import dev.iaiabot.furufuru.data.entity.Content
import dev.iaiabot.furufuru.data.entity.ContentResponse

internal interface ContentRepository {
    suspend fun post(content: Content, path: String): ContentResponse?
}
