package dev.iaiabot.furufuru.feature.ui.issue

import android.text.format.DateFormat
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import dev.iaiabot.furufuru.testtool.initMockOnGroup
import dev.iaiabot.furufuru.usecase.IssueUseCase
import dev.iaiabot.furufuru.usecase.UsernameUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.util.*

object IssueViewModelTest : Spek({
    lateinit var viewModel: IssueViewModel
    val issueUseCase = initMockOnGroup<IssueUseCase>()
    val usernameUseCase = initMockOnGroup<UsernameUseCase>()

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

    describe("#post") {
        beforeEachTest {
            viewModel = IssueViewModel(
                mockk(),
                issueUseCase,
                usernameUseCase,
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
