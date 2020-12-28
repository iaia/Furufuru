package dev.iaiabot.furufuru.repository

import com.google.common.truth.Truth.assertThat
import dev.iaiabot.furufuru.data.github.GithubService
import dev.iaiabot.furufuru.data.github.request.Content
import dev.iaiabot.furufuru.data.github.response.ContentResponse
import dev.iaiabot.furufuru.data.github.response.ContentResponseEntity
import dev.iaiabot.furufuru.util.FurufuruSettings
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
    lateinit var furufuruSettings: FurufuruSettings
    lateinit var service: GithubService

    beforeGroup {
        furufuruSettings = mockk {
            every { githubRepositoryOwner } returns "iaia"
            every { githubRepository } returns "Furufuru"
        }
        service = mockk()
        repository = ContentRepositoryImpl(furufuruSettings, service)
    }

    describe("#post") {
        val content = Content("furufuru", "file")

        beforeGroup {
            coEvery { service.postContent(any(), any(), any(), any()) } returns Response.success(
                200, ContentResponse(
                    ContentResponseEntity(
                        "name",
                        "url",
                        "example.com/html_url.jpg",
                        "example.com/download_url.jpg"
                    )
                )
            )
        }

        context("例外が発生しない場合") {
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
