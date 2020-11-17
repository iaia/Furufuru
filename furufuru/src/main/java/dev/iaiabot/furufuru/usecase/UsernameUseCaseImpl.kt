package dev.iaiabot.furufuru.usecase

import dev.iaiabot.furufuru.data.repository.UserRepository

internal class UsernameUseCaseImpl(
    private val userRepository: UserRepository
) : UsernameUseCase {
    override suspend fun getUsername(): String {
        // application渡したくない
        userRepository.getUserName()
    }

    override suspend fun saveUsername(username: String) {
        // application渡したくない
        userRepository.saveUserName()
    }
}
