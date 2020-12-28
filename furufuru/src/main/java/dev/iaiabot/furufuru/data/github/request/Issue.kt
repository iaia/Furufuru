package dev.iaiabot.furufuru.data.github.request

import kotlinx.serialization.Serializable

@Serializable
internal data class Issue(
    val title: String,
    val body: String? = null,
    val assignee: String? = null,
    val milestone: Int? = null,
    val labels: List<String>? = null,
    val assignees: List<String>? = null
)
