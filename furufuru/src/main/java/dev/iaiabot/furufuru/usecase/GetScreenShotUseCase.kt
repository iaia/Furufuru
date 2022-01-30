package dev.iaiabot.furufuru.usecase

import dev.iaiabot.furufuru.repository.ScreenshotRepository
import kotlinx.coroutines.flow.Flow

interface GetScreenShotUseCase {
    suspend operator fun invoke(): Flow<String?>
}

internal class GetScreenShotUseCaseImpl(
    private val screenshotRepository: ScreenshotRepository,
) : GetScreenShotUseCase {

    override suspend fun invoke() = screenshotRepository.observe()
}
