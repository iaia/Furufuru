package dev.iaiabot.furufuru.usecase

internal interface UsernameUseCase {
    suspend fun getUsername(): String
    suspend fun saveUsername(username: String)
}
