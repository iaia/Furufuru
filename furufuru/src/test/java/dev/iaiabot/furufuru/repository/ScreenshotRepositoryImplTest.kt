package dev.iaiabot.furufuru.repository

import com.google.common.truth.Truth
import dev.iaiabot.furufuru.data.entity.ScreenShot
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifyOrder
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object ScreenshotRepositoryImplTest : Spek({
    lateinit var repository: ScreenshotRepository
    lateinit var screenshot: ScreenShot

    beforeGroup {
        screenshot = mockk()
        repository = ScreenshotRepositoryImpl(screenshot)
    }

    describe("#get") {
        context("未保存のとき") {
            beforeGroup {
                every { screenshot.load() } returns null
                every { screenshot.remove() } returns Unit
            }

            it("nullが返る") {
                Truth.assertThat(repository.load()).isEqualTo(null)
            }

            it("取得したあと削除している") {
                repository.load(true)
                verifyOrder {
                    screenshot.load()
                    screenshot.remove()
                }
            }
        }
        context("保存済みの時") {
            beforeGroup {
                every { screenshot.load() } returns "abcd"
                every { screenshot.remove() } returns Unit
            }

            it("保存済みのものが返る") {
                Truth.assertThat(repository.load()).isEqualTo("abcd")
            }
        }
    }
})
