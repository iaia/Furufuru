package dev.iaiabot.furufuru.data.github.response

import kotlinx.serialization.Serializable

@Serializable
data class IssueResponse(
    val body: String? = null
)
