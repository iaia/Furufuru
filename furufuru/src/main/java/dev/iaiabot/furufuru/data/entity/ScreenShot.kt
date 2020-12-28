package dev.iaiabot.furufuru.data.entity

internal interface ScreenShot {
    fun save(fileStr: String)
    fun load(): String?
    fun remove()
}