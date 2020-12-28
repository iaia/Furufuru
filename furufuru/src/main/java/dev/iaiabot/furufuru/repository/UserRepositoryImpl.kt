package dev.iaiabot.furufuru.repository

import dev.iaiabot.furufuru.data.local.User

internal class UserRepositoryImpl(
    private val user: User
) : UserRepository {

    override fun getUserName(): String {

        return user.getUserName()
    }

    override fun saveUserName(userName: String) {
        user.saveUserName(userName)
    }
}
