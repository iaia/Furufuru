package dev.iaiabot.furufuru.repository

import dev.iaiabot.furufuru.data.local.UserEntity

internal class UserRepositoryImpl(
    private val userEntity: UserEntity
) : UserRepository {

    override fun getUserName(): String {

        return userEntity.getUserName()
    }

    override fun saveUserName(userName: String) {
        userEntity.saveUserName(userName)
    }
}
