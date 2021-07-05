package dev.iaiabot.furufuru.usecase.user

import dev.iaiabot.furufuru.repository.UserRepository

interface LoadUserNameUseCase {
    operator fun invoke(): String
}

internal class LoadUserNameUseCaseImpl(
    private val userRepository: UserRepository
) : LoadUserNameUseCase {

    override fun invoke(): String = userRepository.getUserName()
}
