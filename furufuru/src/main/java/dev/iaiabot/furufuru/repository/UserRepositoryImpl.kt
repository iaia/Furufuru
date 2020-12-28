package dev.iaiabot.furufuru.repository

import android.content.Context

internal class UserRepositoryImpl(
    private val context: Context
) : UserRepository {
    companion object {
        private const val USERNAME = "username"
    }

    override fun getUserName(): String {
        val prefs = context.getSharedPreferences(
            "furufuru",
            Context.MODE_PRIVATE
        )
        return prefs.getString(USERNAME, "") ?: ""
    }

    override fun saveUserName(userName: String) {
        val editor = context.getSharedPreferences(
            "furufuru",
            Context.MODE_PRIVATE
        ).edit()
        editor.putString(USERNAME, userName)
        editor.apply()
    }
}
