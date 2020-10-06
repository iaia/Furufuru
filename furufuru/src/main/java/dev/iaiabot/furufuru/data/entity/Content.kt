package dev.iaiabot.furufuru.data.entity

import kotlinx.serialization.SerialName
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

@Serializable
data class ContentResponse(
    val content: ContentResponseEntity
)

@Serializable
data class ContentResponseEntity(
    val name: String,
    val url: String,
    @SerialName("html_url")
    val htmlUrl: String,
    @SerialName("download_url")
    val downloadUrl: String
)
