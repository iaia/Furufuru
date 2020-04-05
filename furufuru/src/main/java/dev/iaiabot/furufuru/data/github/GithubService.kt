package dev.iaiabot.furufuru.data.github

import dev.iaiabot.furufuru.data.entity.ContentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface GithubService {

    @POST("/repos/{owner}/{repo}/issues")
    suspend fun postIssue(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body issue: Map<String, String>
    ): Response<String>

    @PUT("/repos/{owner}/{repo}/contents/{path}")
    suspend fun postContent(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body content: Map<String, String>,
        @Path("path") path: String
    ): Response<ContentResponse>
}
