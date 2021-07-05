package dev.iaiabot.furufuru.data.entity

import kotlinx.coroutines.flow.Flow

internal interface ScreenShot {
    val screenShotFlow: Flow<String?>

    suspend fun save(fileStr: String)
    fun load(): String?
    fun remove()
}
