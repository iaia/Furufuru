package dev.iaiabot.furufuru.usecase

internal interface ScreenShotUseCase {
    suspend fun uploadScreenShot(): ScreenShotUseCase.ImageUrls?

    data class ImageUrls(
        val fileUrl: String,
        val imageUrl: String
    )
}
