package dev.iaiabot.furufuru.usecase.user

import dev.iaiabot.furufuru.repository.UserRepository

internal class UsernameUseCaseImpl(
    private val userRepository: UserRepository
) : UsernameUseCase {
    override suspend fun load(): String {
        return userRepository.getUserName()
    }

    override suspend fun save(username: String) {
        userRepository.saveUserName(username)
    }
}
