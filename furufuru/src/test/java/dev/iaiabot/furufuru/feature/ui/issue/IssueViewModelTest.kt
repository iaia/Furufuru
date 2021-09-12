package dev.iaiabot.furufuru.feature.ui.issue

import android.text.format.DateFormat
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import com.google.common.truth.Truth.assertThat
import dev.iaiabot.furufuru.testtool.initMockOnGroup
import dev.iaiabot.furufuru.usecase.GetScreenShotUseCase
import dev.iaiabot.furufuru.usecase.PostIssueUseCase
import dev.iaiabot.furufuru.usecase.user.LoadUserNameUseCase
import dev.iaiabot.furufuru.usecase.user.SaveUsernameUseCase
import dev.iaiabot.furufuru.util.GithubSettings
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.util.*

internal object IssueViewModelTest : Spek({
    lateinit var viewModel: IssueViewModel
    val saveUserNameUseCase = initMockOnGroup<SaveUsernameUseCase>()
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
            viewModel.init()

            assertThat(viewModel.fileStr.value).isEqualTo("aaa")
            assertThat(viewModel.userName.value).isEqualTo("bbb")
            assertThat(viewModel.labels.value).isEqualTo("ccc")
        }
    }

    describe("#post") {
        beforeEachTest {
            viewModel = IssueViewModel(
                mockk(),
                saveUserNameUseCase,
                loadUserNameUseCase,
                githubSettings,
                postIssueUseCase,
                getScreenShotUseCase,
            )
            viewModel.init()
            viewModel.title.value = "title"
            viewModel.body.value = "body"
        }

        it("verify") {
            viewModel.post()
        }
    }
})
