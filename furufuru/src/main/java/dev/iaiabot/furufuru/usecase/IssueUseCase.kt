package dev.iaiabot.furufuru.usecase

internal interface IssueUseCase {
    suspend fun post(title: String, userName: String, body: String)
}
