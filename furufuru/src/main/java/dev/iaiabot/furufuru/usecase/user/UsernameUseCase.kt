package dev.iaiabot.furufuru.usecase.user

internal interface UsernameUseCase {
    suspend fun load(): String
    suspend fun save(username: String)
}
