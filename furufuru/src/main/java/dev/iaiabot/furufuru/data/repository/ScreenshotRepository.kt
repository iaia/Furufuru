package dev.iaiabot.furufuru.data.repository

import android.content.Context

interface ScreenshotRepository {
   fun save(context: Context, fileStr: String)
   fun get(context: Context): String?
}
