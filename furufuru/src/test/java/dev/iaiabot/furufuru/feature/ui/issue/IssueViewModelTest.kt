package dev.iaiabot.furufuru.feature.ui.issue

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.furufuru.usecase.GetScreenShotUseCase
import dev.iaiabot.furufuru.usecase.PostIssueUseCase
import dev.iaiabot.furufuru.usecase.user.LoadUserNameUseCase
import dev.iaiabot.furufuru.util.GithubSettings
import dev.iaiabot.furufuru.viewModelTestRule
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest

@ExperimentalCoroutinesApi
internal object IssueViewModelTest : DescribeSpec({
    lateinit var viewModel: IssueViewModel
    lateinit var loadUserNameUseCase: LoadUserNameUseCase
    lateinit var githubSettings: GithubSettings
    lateinit var postIssueUseCase: PostIssueUseCase
    lateinit var getScreenShotUseCase: GetScreenShotUseCase
    val testDispatcher = TestCoroutineDispatcher()

    viewModelTestRule(testDispatcher)

    beforeTest {
        loadUserNameUseCase = mockk() {
            every { this@mockk.invoke() } returns "bbb"
        }
        getScreenShotUseCase = mockk() {
            coEvery { this@mockk.invoke() } returns flow { emit("aaa") }
        }
        githubSettings = mockk() {
            every { labels } returns mutableListOf("ccc")
        }
        postIssueUseCase = mockk(relaxed = true)
    }

    describe("#init") {
        it("初期化されている") {
            testDispatcher.runBlockingTest {
                viewModel = IssueViewModelImpl(
                    loadUserNameUseCase,
                    githubSettings,
                    postIssueUseCase,
                    getScreenShotUseCase,
                )
                viewModel.imageStrBase64.observeForever {}
                viewModel.labels.observeForever {}

                assertThat(viewModel.imageStrBase64.value).isEqualTo("aaa")
                assertThat(viewModel.userName.value).isEqualTo("bbb")
                assertThat(viewModel.labels.value).containsExactly("ccc")
            }
        }
    }

    describe("#post") {
        beforeTest {
            viewModel = IssueViewModelImpl(
                loadUserNameUseCase,
                githubSettings,
                postIssueUseCase,
                getScreenShotUseCase,
            )
        }

        it("verify") {
            viewModel.post()
            coVerify {
                postIssueUseCase(
                    any(), any(), any(), any()
                )
            }
        }
    }
})
