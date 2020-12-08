package dev.iaiabot.furufuru.usecase

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.furufuru.data.repository.ContentRepository
import dev.iaiabot.furufuru.data.repository.IssueRepository
import dev.iaiabot.furufuru.data.repository.ScreenshotRepository
import dev.iaiabot.furufuru.feature.ui.issue.IssueBodyTemplate
import dev.iaiabot.furufuru.testtool.initMockOnGroup
import dev.iaiabot.furufuru.util.FurufuruSettings
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.fail
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

internal object IssueUseCaseImplTest : Spek({
    lateinit var usecase: IssueUseCase
    val issueRepository = initMockOnGroup<IssueRepository>()
    val screenshotRepository = initMockOnGroup<ScreenshotRepository>()
    val contentRepository = initMockOnGroup<ContentRepository>()
    val furufuruSettings = initMockOnGroup<FurufuruSettings>()

    describe("#getScreenShot()") {
        context("1発で取得出来る場合") {
            beforeGroup {
                every { screenshotRepository.get() } returns "SCREEN_SHOT"
                usecase = IssueUseCaseImpl(
                    issueRepository,
                    screenshotRepository,
                    contentRepository,
                    furufuruSettings
                )
            }

            it("null以外が返る") {
                val result = runBlocking {
                    usecase.getScreenShot()
                }
                assertThat(result).isNotEmpty()
            }
        }

        context("リトライしても取り出せない場合") {
            beforeGroup {
                every { screenshotRepository.get() } returns null
                usecase = IssueUseCaseImpl(
                    issueRepository,
                    screenshotRepository,
                    contentRepository,
                    furufuruSettings
                )
            }

            it("nullが返る") {
                val result = runBlocking {
                    usecase.getScreenShot(retryNum = 1)
                }
                assertThat(result).isNull()
                verify(exactly = 2) { screenshotRepository.get() }
            }
        }
    }

    describe("#post") {
        beforeGroup {
            every { screenshotRepository.get(any()) } returns "SCREEN_SHOT"
            coEvery { contentRepository.post(any(), any()) } returns mockk() {
                every { fileUrl } returns "file"
                every { imageUrl } returns "image"
            }
            every { furufuruSettings.furufuruBranch } returns "furufuru-branch"
            mockkObject(IssueBodyTemplate)
            every { IssueBodyTemplate.createBody(any(), any(), any(), any()) } returns "ISSUE_BODY"
            coEvery { issueRepository.post(any()) } returns Unit
        }

        context("成功するとき") {
            it("例外が発生しない") {
                runBlocking {
                    usecase.post("title", "username", "body")
                }
            }
        }

        context("失敗するとき") {
            beforeGroup {
                coEvery { issueRepository.post(any()) } throws Exception("exception")
            }

            it("例外が発生する") {
                try {
                    runBlocking {
                        usecase.post("title", "username", "body")
                    }
                    fail("has no exception")
                } catch (e: Exception) {
                    assertThat(e).hasCauseThat()
                }
            }
        }
    }
})
