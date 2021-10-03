package dev.iaiabot.furufuru.repository

import com.google.common.truth.Truth
import dev.iaiabot.furufuru.data.local.ScreenshotDataSource
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object ScreenshotRepositoryImplTest : Spek({
    lateinit var repository: ScreenshotRepository
    lateinit var screenshotDataSource: ScreenshotDataSource

    beforeEachTest {
        repository = ScreenshotRepositoryImpl(screenshotDataSource)
    }

    describe("#get") {
        context("未保存のとき") {
            beforeGroup {
                screenshotDataSource = mockk() {
                    every { screenShotFlow } returns flow { emit(null) }
                }
            }

            it("nullが返る") {
                runBlocking {
                    Truth.assertThat(repository.screenShotFlow.single()).isEqualTo(null)
                }
            }
        }

        context("保存済みの時") {
            beforeGroup {
                screenshotDataSource = mockk() {
                    every { screenShotFlow } returns flow { emit("abcd") }
                }
            }

            it("保存済みのものが返る") {
                runBlocking {
                    Truth.assertThat(repository.screenShotFlow.single()).isEqualTo("abcd")
                }
            }
        }
    }
})
