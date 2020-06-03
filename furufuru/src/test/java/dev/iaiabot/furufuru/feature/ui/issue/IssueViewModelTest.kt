package dev.iaiabot.furufuru.feature.ui.issue

import android.text.format.DateFormat
import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import dev.iaiabot.furufuru.data.entity.ContentResponse
import dev.iaiabot.furufuru.data.repository.ContentRepository
import dev.iaiabot.furufuru.data.repository.IssueRepository
import io.mockk.*
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.util.*

object IssueViewModelTest : Spek({
    lateinit var viewModel: IssueViewModel
    lateinit var issueRepository: IssueRepository
    lateinit var contentRepository: ContentRepository
    lateinit var contentResponse: ContentResponse
    val fileStr: String? = "path/to"

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

        contentResponse = mockk()
        every { contentResponse.content.htmlUrl } returns "example.com/html_1.jpg"
        every { contentResponse.content.downloadUrl } returns "example.com/download_1.jpg"

        coEvery { issueRepository.post(any()) } answers {}
        coEvery { contentRepository.post(any(), any()) } returns contentResponse

        mockkStatic(DateFormat::class)
        every { DateFormat.format(any(), any<Date>()) } returns "aaa"
    }

    afterEachTest {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }

    describe("#post") {
        beforeEachTest {
            viewModel = IssueViewModel(issueRepository, contentRepository, fileStr)
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
