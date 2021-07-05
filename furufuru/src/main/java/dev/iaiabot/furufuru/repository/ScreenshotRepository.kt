package dev.iaiabot.furufuru.repository

import kotlinx.coroutines.flow.Flow

internal interface ScreenshotRepository {
   val screenShotFlow: Flow<String?>

   suspend fun save(fileStr: String)
   fun load(remove: Boolean = false): String?
}
