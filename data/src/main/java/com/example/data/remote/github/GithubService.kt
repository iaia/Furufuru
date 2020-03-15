package com.example.data.remote.github

import com.example.data.entity.ContentResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface GithubService {

    @POST("/repos/{owner}/{repo}/issues")
    suspend fun postIssue(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body issue: Map<String, String?>
    ): Response<String>

    @PUT("/repos/{owner}/{repo}/contents/{path}")
    suspend fun postContent(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body content: Map<String, String?>,
        @Path("path") path: String
    ): Response<ContentResponse>
}
