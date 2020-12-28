package dev.iaiabot.furufuru.repository

import dev.iaiabot.furufuru.data.github.request.Issue

internal interface IssueRepository {
    suspend fun post(issue: Issue)
}
