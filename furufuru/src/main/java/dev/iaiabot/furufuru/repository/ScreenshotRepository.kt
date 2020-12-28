package dev.iaiabot.furufuru.repository

internal interface ScreenshotRepository {
   fun save(fileStr: String)
   fun get(remove: Boolean = false): String?
}
