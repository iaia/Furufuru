package dev.iaiabot.furufuru.repository

import kotlinx.coroutines.flow.Flow

internal interface ScreenshotRepository {
   suspend fun save(fileStr: String)
   suspend fun load(remove: Boolean = false): String?
   suspend fun remove()
   suspend fun observe(): Flow<String?>
}
