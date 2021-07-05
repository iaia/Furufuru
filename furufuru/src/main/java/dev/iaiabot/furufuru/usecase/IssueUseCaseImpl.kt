package dev.iaiabot.furufuru.usecase

import dev.iaiabot.furufuru.repository.ContentRepository
import dev.iaiabot.furufuru.repository.IssueRepository
import dev.iaiabot.furufuru.repository.ScreenshotRepository
import dev.iaiabot.furufuru.util.GithubSettings
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import java.util.*

internal class IssueUseCaseImpl(
    private val issueRepository: IssueRepository,
    private val screenshotRepository: ScreenshotRepository,
    private val contentRepository: ContentRepository,
    private val githubSettings: GithubSettings,
) : IssueUseCase {

    override val screenShotFlow: Flow<String?> = screenshotRepository.screenShotFlow

    override suspend fun getScreenShot(retryNum: Int): String? {
        repeat(retryNum) { repeatNum ->
            val screenshot = screenshotRepository.load()
            if (screenshot == null) {
                delay(1000L * repeatNum)
                return@repeat
            } else {
                return screenshot
            }
        }
        return null
    }
}
