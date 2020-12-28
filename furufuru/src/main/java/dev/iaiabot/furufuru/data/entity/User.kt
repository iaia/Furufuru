package dev.iaiabot.furufuru.data.entity

interface User {
    fun getUserName(): String
    fun saveUserName(userName: String)
}