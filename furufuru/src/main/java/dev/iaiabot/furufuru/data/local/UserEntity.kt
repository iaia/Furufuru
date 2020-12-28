package dev.iaiabot.furufuru.data.local

import android.content.SharedPreferences

internal class UserEntity(
    private val preferences: SharedPreferences
) {
    companion object {
        private const val USERNAME = "username"
    }

    fun getUserName(): String {
        return preferences.getString(USERNAME, "") ?: ""
    }

    fun saveUserName(userName: String) {
        val editor = preferences.edit()
        editor.putString(USERNAME, userName)
        editor.apply()
    }
}