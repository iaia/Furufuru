package dev.iaiabot.furufuru.repository

internal interface UserRepository {
    fun getUserName(): String
    fun saveUserName(userName: String)
}
