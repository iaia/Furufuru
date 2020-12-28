package dev.iaiabot.furufuru.data.github.response

import kotlinx.serialization.Serializable

@Serializable
internal data class ContentResponse(
    val content: ContentInfoResponse
)
