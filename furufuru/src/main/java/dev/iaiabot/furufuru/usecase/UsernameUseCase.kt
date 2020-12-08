package dev.iaiabot.furufuru.usecase

internal interface UsernameUseCase {
    suspend fun load(): String
    suspend fun save(username: String)
}
