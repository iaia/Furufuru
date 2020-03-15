package com.example.data.remote.github

import com.example.data.entity.Issue
import retrofit2.Response
import retrofit2.http.*

interface GithubService {

    @POST("/repos/{owner}/{repo}/issues")
    suspend fun postIssue(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body issue: Map<String, String?>
    ): Response<String>
}
