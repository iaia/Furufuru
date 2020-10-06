package dev.iaiabot.furufuru.data.repository

import android.content.Context

interface UserRepository {
    fun getUserName(context: Context): String
    fun saveUserName(context: Context, userName: String)
}
