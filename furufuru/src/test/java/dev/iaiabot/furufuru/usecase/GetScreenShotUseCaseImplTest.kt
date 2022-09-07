package dev.iaiabot.furufuru.usecase

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.furufuru.furufuruTestRule
import dev.iaiabot.furufuru.repository.ScreenshotRepository
import dev.iaiabot.furufuru.testtool.initMockOnGroup
import io.mockk.coEvery
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object GetScreenShotUseCaseImplTest : Spek({
    lateinit var usecase: GetScreenShotUseCase
    val screenshotRepository = initMockOnGroup<ScreenshotRepository>()

    furufuruTestRule()

    describe("#getScreenShot()") {
        context("スクリーンショットがある場合") {
            beforeGroup {
                coEvery { screenshotRepository.observe() } returns flow { emit("SCREEN_SHOT") }
                usecase = GetScreenShotUseCaseImpl(
                    screenshotRepository,
                )
            }

            it("null以外が返る") {
                runBlocking {
                    val result = usecase()
                    assertThat(result).isNotNull()
                }
            }
        }
    }
})
