package com.example.data.remote.github

import com.example.data.entity.Content
import com.example.data.entity.ContentResponse
import com.example.data.entity.Issue
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
        @Body issue: Issue
    ): Response<String>

    @PUT("/repos/{owner}/{repo}/contents/{path}")
    suspend fun postContent(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body content: Content,
        @Path("path") path: String
    ): Response<ContentResponse>
}
