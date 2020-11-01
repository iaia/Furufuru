package dev.iaiabot.furufuru.data.repository

import android.content.Context

internal class UserRepositoryImpl : UserRepository {
    companion object {
        private const val USERNAME = "username"
    }

    override fun getUserName(context: Context): String {
        val prefs = context.getSharedPreferences(
            "furufuru",
            Context.MODE_PRIVATE
        )
        return prefs.getString(USERNAME, "") ?: ""
    }

    override fun saveUserName(context: Context, userName: String) {
        val editor = context.getSharedPreferences(
            "furufuru",
            Context.MODE_PRIVATE
        ).edit()
        editor.putString(USERNAME, userName)
        editor.apply()
    }
}
