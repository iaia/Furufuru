package dev.iaiabot.furufuru.data.entity

import kotlinx.serialization.Serializable

@Serializable
data class Content(
    val message: String,
    val content: String,
    val sha: String? = null,
    val branch: String? = null,
    val committer: String? = null,
    val author: String? = null
)
