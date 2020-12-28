package dev.iaiabot.furufuru.data.local

import android.content.SharedPreferences
import dev.iaiabot.furufuru.data.entity.User

internal class UserDataSource(
    private val preferences: SharedPreferences
) : User {
    companion object {
        private const val USERNAME = "username"
    }

    override fun getUserName(): String {
        return preferences.getString(USERNAME, "") ?: ""
    }

    override fun saveUserName(userName: String) {
        val editor = preferences.edit()
        editor.putString(USERNAME, userName)
        editor.apply()
    }
}