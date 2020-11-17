package dev.iaiabot.furufuru.usecase

internal interface ScreenShotUseCase {
    suspend fun uploadScreenShot(): ScreenShotUseCase.ImageUrls? {
        throw NotImplementedError()
    }

    suspend fun getScreenShot(): String? {
        throw NotImplementedError()
    }

    // FIXME: これどっかに持ってく
    data class ImageUrls(
        val fileUrl: String,
        val imageUrl: String
    )
}
