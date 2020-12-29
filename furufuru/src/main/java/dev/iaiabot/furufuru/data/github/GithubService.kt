package dev.iaiabot.furufuru.data.github

import dev.iaiabot.furufuru.data.github.request.Content
import dev.iaiabot.furufuru.data.github.request.Issue
import dev.iaiabot.furufuru.data.github.response.ContentResponse
import dev.iaiabot.furufuru.data.github.response.IssueResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

internal interface GithubService {

    @POST("/repos/{owner}/{repo}/issues")
    suspend fun postIssue(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body issue: Issue
    ): Response<IssueResponse>

    @PUT("/repos/{owner}/{repo}/contents/{path}")
    suspend fun postContent(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body content: Content,
        @Path("path") path: String
    ): Response<ContentResponse>
}
