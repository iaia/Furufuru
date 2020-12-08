package dev.iaiabot.furufuru.data.repository

internal interface ScreenshotRepository {
   fun save(fileStr: String)
   fun get(remove: Boolean = false): String?
}
