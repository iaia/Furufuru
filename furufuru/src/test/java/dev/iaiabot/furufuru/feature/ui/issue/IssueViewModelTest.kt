package dev.iaiabot.furufuru.feature.ui.issue

import android.text.format.DateFormat
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import dev.iaiabot.furufuru.data.entity.ContentResponse
import dev.iaiabot.furufuru.data.repository.ContentRepository
import dev.iaiabot.furufuru.data.repository.IssueRepository
import dev.iaiabot.furufuru.data.repository.ScreenshotRepository
import dev.iaiabot.furufuru.data.repository.UserRepository
import dev.iaiabot.furufuru.util.FurufuruSettings
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.util.*

object IssueViewModelTest : Spek({
    lateinit var viewModel: IssueViewModel
    lateinit var issueRepository: IssueRepository
    lateinit var contentRepository: ContentRepository
    lateinit var contentResponse: ContentResponse
    lateinit var screenshotRepository: ScreenshotRepository
    lateinit var userRepository: UserRepository
    lateinit var furufuruSettings: FurufuruSettings

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

        issueRepository = mockk()
        contentRepository = mockk()
        screenshotRepository = mockk()
        userRepository = mockk()
        furufuruSettings = mockk()
        contentResponse = mockk()

        every { contentResponse.content.htmlUrl } returns "example.com/html_1.jpg"
        every { contentResponse.content.downloadUrl } returns "example.com/download_1.jpg"

        coEvery { issueRepository.post(any()) } answers {}
        coEvery { userRepository.getUserName(any()) } returns "user name"
        coEvery { userRepository.saveUserName(any(), any()) } returns Unit
        coEvery { contentRepository.post(any(), any()) } returns contentResponse
        every { screenshotRepository.get() } returns "/path/to"
        every { furufuruSettings.furufuruBranch } returns "furufuru-branch"

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
                issueRepository,
                contentRepository,
                screenshotRepository,
                furufuruSettings,
                userRepository
            )
            viewModel.init()
            viewModel.title.value = "title"
            viewModel.body.value = "body"
        }

        it("verify") {
            viewModel.post()
            coVerify(exactly = 1) { contentRepository.post(any(), any()) }
            coVerify(exactly = 1) { issueRepository.post(any()) }
        }
    }
})
