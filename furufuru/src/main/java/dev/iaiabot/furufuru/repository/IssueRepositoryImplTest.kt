package dev.iaiabot.furufuru.repository

import dev.iaiabot.furufuru.data.github.GithubService
import dev.iaiabot.furufuru.data.github.request.Issue
import dev.iaiabot.furufuru.data.github.response.IssueResponse
import dev.iaiabot.furufuru.util.FurufuruSettings
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.fail
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import retrofit2.Response

internal object IssueRepositoryImplTest : Spek({
    lateinit var repository: IssueRepository
    lateinit var furufuruSettings: FurufuruSettings
    lateinit var service: GithubService

    beforeGroup {
        furufuruSettings = mockk {
            every { githubRepositoryOwner } returns "iaia"
            every { githubRepository } returns "Furufuru"
        }
        service = mockk()
        repository = IssueRepositoryImpl(furufuruSettings, service)
    }

    describe("#post") {
        val issue = Issue("title", "body")
        beforeGroup {
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
