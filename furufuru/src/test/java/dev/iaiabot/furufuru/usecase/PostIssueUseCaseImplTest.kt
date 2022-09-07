package dev.iaiabot.furufuru.usecase

import com.google.common.truth.Truth
import dev.iaiabot.furufuru.feature.ui.issue.IssueBodyTemplate
import dev.iaiabot.furufuru.repository.ContentRepository
import dev.iaiabot.furufuru.repository.IssueRepository
import dev.iaiabot.furufuru.repository.ScreenshotRepository
import dev.iaiabot.furufuru.testtool.initMockOnGroup
import dev.iaiabot.furufuru.usecase.user.SaveUsernameUseCase
import dev.iaiabot.furufuru.util.GithubSettings
import io.mockk.coEvery
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.mockkObject
import kotlinx.coroutines.runBlocking
import org.junit.Assert.fail
import org.junit.jupiter.api.extension.ExtendWith
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

@ExtendWith(MockKExtension::class)
internal object PostIssueUseCaseImplTest : Spek({
    lateinit var usecase: PostIssueUseCase
    val issueRepository = initMockOnGroup<IssueRepository>()
    val screenshotRepository = initMockOnGroup<ScreenshotRepository>()
    val contentRepository = initMockOnGroup<ContentRepository>()
    val githubSettings = initMockOnGroup<GithubSettings>()
    val saveUsernameUseCase = initMockOnGroup<SaveUsernameUseCase>()

    describe("#post") {
        beforeGroup {
            coEvery { screenshotRepository.load(any()) } returns "SCREEN_SHOT"
            coEvery { screenshotRepository.remove() } returns Unit
            coEvery { contentRepository.post(any(), any()) } returns mockk() {
                every { fileUrl } returns "file"
                every { imageUrl } returns "image"
            }
            every { githubSettings.furufuruBranch } returns "furufuru-branch"
            every { IssueBodyTemplate.createBody(any(), any(), any(), any()) } returns "ISSUE_BODY"
            coEvery { issueRepository.post(any()) } returns Unit
            coEvery { saveUsernameUseCase(any()) } returns Unit
        }

        beforeEachTest {
            usecase = PostIssueUseCaseImpl(
                issueRepository,
                screenshotRepository,
                contentRepository,
                githubSettings,
                saveUsernameUseCase
            )
        }

        context("成功するとき") {
            it("例外が発生しない") {
                runBlocking {
                    usecase("title", "username", "body")
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
                        usecase("title", "username", "body")
                    }
                    fail("has no exception")
                } catch (e: Exception) {
                    Truth.assertThat(e).hasCauseThat()
                }
            }
        }
    }
})
