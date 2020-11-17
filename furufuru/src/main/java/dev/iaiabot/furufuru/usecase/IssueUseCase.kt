package dev.iaiabot.furufuru.usecase

import dev.iaiabot.furufuru.data.entity.Issue

internal interface IssueUseCase {
    suspend fun post(issue: Issue)
}
