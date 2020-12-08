package dev.iaiabot.furufuru.usecase

internal interface IssueUseCase {
    suspend fun post(title: String, userName: String, body: String)
    suspend fun getScreenShot(): String?
    suspend fun uploadScreenShot(): ImageUrls?

    // FIXME: これどっかに持ってく
    data class ImageUrls(
        val fileUrl: String,
        val imageUrl: String
    )
}
