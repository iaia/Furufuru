package dev.iaiabot.furufuru.data.repository

interface ScreenshotRepository {
   fun save(fileStr: String)
   fun get(): String?
}
