package dev.iaiabot.furufuru.data.repository

import dev.iaiabot.furufuru.data.entity.Issue
import dev.iaiabot.furufuru.data.remote.github.GithubService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class IssueRepositoryImplTest {
    lateinit var repository: IssueRepository
    @MockK
    lateinit var service: GithubService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        repository = IssueRepositoryImpl("owner", "repository", service)
        coEvery { service.postIssue(any(), any(), any()) } returns Response.success("success")
    }

    @Test
    fun test() = runBlocking {
        val issue = Issue("title")
        repository.post(issue)

    }
}
