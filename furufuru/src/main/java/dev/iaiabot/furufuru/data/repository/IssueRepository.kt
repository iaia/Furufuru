package dev.iaiabot.furufuru.data.repository

import dev.iaiabot.furufuru.data.entity.Issue

interface IssueRepository {
    suspend fun post(issue: Issue)
}
