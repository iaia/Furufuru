package dev.iaiabot.furufuru.repository

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.furufuru.data.github.GithubService
import dev.iaiabot.furufuru.data.github.request.Content
import dev.iaiabot.furufuru.data.github.response.ContentInfoResponse
import dev.iaiabot.furufuru.data.github.response.ContentResponse
import dev.iaiabot.furufuru.util.GithubSettings
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.fail
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import retrofit2.Response

internal object ContentRepositoryImplTest : Spek({
    lateinit var repository: ContentRepository
    lateinit var githubSettings: GithubSettings
    lateinit var service: GithubService

    beforeEachTest {
        githubSettings = mockk {
            every { githubRepositoryOwner } returns "iaia"
            every { githubRepository } returns "Furufuru"
        }
        repository = ContentRepositoryImpl(githubSettings, service)
    }

    describe("#post") {
        val content = Content("furufuru", "file")

        context("例外が発生しない場合") {
            beforeGroup {
                service = mockk() {
                    coEvery { postContent(any(), any(), any(), any()) } returns Response.success(
                        201, ContentResponse(
                            ContentInfoResponse(
                                "name",
                                "url",
                                "example.com/html_url.jpg",
                                "example.com/download_url.jpg"
                            )
                        )
                    )
                }
            }

            it("例外が発生しない") {
                runBlocking {
                    try {
                        repository.post(content, "/path/to")
                    } catch (e: Exception) {
                        fail()
                    }
                }
            }

            it("値が返る") {
                runBlocking {
                    val response = repository.post(content, "/path/to") ?: fail()
                    with(response) {
                        assertThat(imageUrl).endsWith("download_url.jpg")
                        assertThat(fileUrl).endsWith("html_url.jpg")
                    }
                }
            }
        }
    }
})
