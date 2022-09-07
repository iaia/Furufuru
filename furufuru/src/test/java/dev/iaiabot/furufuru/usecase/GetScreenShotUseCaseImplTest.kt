package dev.iaiabot.furufuru.usecase

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.furufuru.feature.ui.issue.IssueBodyTemplate
import dev.iaiabot.furufuru.repository.ScreenshotRepository
import dev.iaiabot.furufuru.testtool.initMockOnGroup
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockkObject
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@ExtendWith(MyExtension::class)
internal object GetScreenShotUseCaseImplTest : Spek({
    lateinit var usecase: GetScreenShotUseCase
    val screenshotRepository = initMockOnGroup<ScreenshotRepository>()

    describe("#getScreenShot()") {
        context("スクリーンショットがある場合") {
            beforeGroup {
                mockkObject(IssueBodyTemplate)
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

open class MyExtension: Extension, BeforeEachCallback, BeforeAllCallback, AfterAllCallback {

    override fun beforeEach(context: ExtensionContext?) {
        println("furufuru before each")
    }

    override fun beforeAll(context: ExtensionContext?) {
        println("furufuru before all")
    }

    override fun afterAll(context: ExtensionContext?) {
        println("furufuru after all")
    }
}
