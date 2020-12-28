package dev.iaiabot.furufuru.data.entity

internal interface ScreenShot {
    fun save(fileStr: String)
    fun get(): String?
    fun remove()
}