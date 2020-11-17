package dev.iaiabot.furufuru.usecase

import dev.iaiabot.furufuru.data.repository.ScreenshotRepository
import kotlinx.coroutines.delay

internal class GetScreenShotUseCaseImpl(
    private val screenshotRepository: ScreenshotRepository
) : GetScreenShotUseCase {

    override suspend fun getScreenShot(): String? {
        repeat(3) { repeatNum ->
            val screenshot = screenshotRepository.get()
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
