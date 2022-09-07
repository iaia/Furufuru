package dev.iaiabot.furufuru.repository

import dev.iaiabot.furufuru.data.github.GithubService
import dev.iaiabot.furufuru.data.github.request.Issue
import dev.iaiabot.furufuru.data.github.response.IssueResponse
import dev.iaiabot.furufuru.furufuruTestRule
import dev.iaiabot.furufuru.util.GithubSettings
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.fail
import retrofit2.Response

internal object IssueRepositoryImplTest : DescribeSpec({
    lateinit var repository: IssueRepository
    lateinit var githubSettings: GithubSettings
    lateinit var service: GithubService

    furufuruTestRule()

    beforeTest {
        githubSettings = mockk {
            every { githubRepositoryOwner } returns "iaia"
            every { githubRepository } returns "Furufuru"
        }
        service = mockk()
        repository = IssueRepositoryImpl(githubSettings, service)
    }

    describe("#post") {
        val issue = Issue("title", "body")
        beforeTest {
            coEvery { service.postIssue(any(), any(), any()) } returns Response.success(
                200,
                IssueResponse()
            )
        }

        context("例外が発生しない場合") {
            it("例外が発生しない") {
                runBlocking {
                    try {
                        repository.post(issue)
                    } catch (e: Exception) {
                        fail()
                    }
                }
            }
        }
    }
})
