package dev.iaiabot.furufuru.feature.ui.issue

import android.text.format.DateFormat
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.google.common.truth.Truth.assertThat
import dev.iaiabot.furufuru.testtool.initMockOnGroup
import dev.iaiabot.furufuru.usecase.GetScreenShotUseCase
import dev.iaiabot.furufuru.usecase.PostIssueUseCase
import dev.iaiabot.furufuru.usecase.user.LoadUserNameUseCase
import dev.iaiabot.furufuru.util.GithubSettings
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.verify
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.util.*

internal object IssueViewModelTest : Spek({
    lateinit var viewModel: IssueViewModel
    val loadUserNameUseCase = initMockOnGroup<LoadUserNameUseCase>()
    val githubSettings = initMockOnGroup<GithubSettings>()
    val postIssueUseCase = initMockOnGroup<PostIssueUseCase>()
    val getScreenShotUseCase = initMockOnGroup<GetScreenShotUseCase>()

    beforeEachTest {
        ArchTaskExecutor
            .getInstance()
            .setDelegate(object : TaskExecutor() {
                override fun executeOnDiskIO(runnable: Runnable) {
                    runnable.run()
                }

                override fun isMainThread(): Boolean {
                    return true
                }

                override fun postToMainThread(runnable: Runnable) {
                    runnable.run()
                }

            })

        mockkStatic(DateFormat::class)
        every { DateFormat.format(any(), any<Date>()) } returns "aaa"

        mockkObject(IssueBodyTemplate)
        every { IssueBodyTemplate.createBody(any(), any(), any(), any()) } returns "aaaa"
    }

    afterEachTest {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }

    describe("#init") {
        it("初期化されている") {
            viewModel = IssueViewModelImpl(
                loadUserNameUseCase,
                githubSettings,
                postIssueUseCase,
                getScreenShotUseCase,
            )

            assertThat(viewModel.imageStrBase64.value).isEqualTo("aaa")
            assertThat(viewModel.userName.value).isEqualTo("bbb")
            assertThat(viewModel.labels.value).isEqualTo("ccc")
        }
    }

    describe("#post") {
        beforeEachTest {
            viewModel = IssueViewModelImpl(
                loadUserNameUseCase,
                githubSettings,
                postIssueUseCase,
                getScreenShotUseCase,
            )
            viewModel.onTitleChange("title")
            viewModel.onBodyChange("body")
        }

        it("verify") {
            verify { viewModel.post() }
        }
    }
})
