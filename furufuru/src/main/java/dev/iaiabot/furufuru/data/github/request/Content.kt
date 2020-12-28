package dev.iaiabot.furufuru.data.github.request

import kotlinx.serialization.Serializable

@Serializable
internal data class Content(
    val message: String = "[ci skip] Upload furufuru image",
    val content: String,
    val sha: String? = null,
    val branch: String? = null,
    val committer: String? = null,
)
