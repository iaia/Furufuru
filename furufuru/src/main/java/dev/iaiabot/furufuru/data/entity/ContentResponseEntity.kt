package dev.iaiabot.furufuru.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentResponseEntity(
    val name: String,
    val url: String,
    @SerialName("html_url")
    val htmlUrl: String,
    @SerialName("download_url")
    val downloadUrl: String
)
