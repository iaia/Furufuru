package dev.iaiabot.furufuru.repository

import dev.iaiabot.furufuru.data.local.UserDataSource

internal class UserRepositoryImpl(
    private val userDataSource: UserDataSource
) : UserRepository {

    override fun getUserName(): String {
        return userDataSource.getUserName()
    }

    override fun saveUserName(userName: String) {
        userDataSource.saveUserName(userName)
    }
}
