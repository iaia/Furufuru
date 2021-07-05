package dev.iaiabot.furufuru.usecase

import kotlinx.coroutines.flow.Flow


internal interface IssueUseCase {
    val screenShotFlow: Flow<String?>

    suspend fun getScreenShot(retryNum: Int = 3): String?
}
