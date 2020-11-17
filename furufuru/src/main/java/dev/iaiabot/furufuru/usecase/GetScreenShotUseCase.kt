package dev.iaiabot.furufuru.usecase

internal interface GetScreenShotUseCase {
    suspend fun getScreenShot(): String?
}
