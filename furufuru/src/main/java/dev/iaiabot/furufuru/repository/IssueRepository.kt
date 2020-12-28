package dev.iaiabot.furufuru.repository

import dev.iaiabot.furufuru.data.entity.Issue

internal interface IssueRepository {
    suspend fun post(issue: Issue)
}
