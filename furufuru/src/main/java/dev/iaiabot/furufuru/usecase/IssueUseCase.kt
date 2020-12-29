package dev.iaiabot.furufuru.usecase


internal interface IssueUseCase {
    suspend fun post(
        title: String,
        userName: String,
        body: String,
        labels: List<String> = emptyList()
    )

    suspend fun getScreenShot(retryNum: Int = 3): String?
}
