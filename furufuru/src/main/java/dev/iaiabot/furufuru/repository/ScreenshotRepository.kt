package dev.iaiabot.furufuru.repository

internal interface ScreenshotRepository {
   fun save(fileStr: String)
   fun load(remove: Boolean = false): String?
}
