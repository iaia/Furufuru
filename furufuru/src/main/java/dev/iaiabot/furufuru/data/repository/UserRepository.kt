package dev.iaiabot.furufuru.data.repository

internal interface UserRepository {
    fun getUserName(): String
    fun saveUserName(userName: String)
}
