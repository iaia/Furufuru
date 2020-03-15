package com.example.data.remote.github

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface GithubService {

    @POST("/repos/{owner}/{repo}/issues")
    suspend fun postIssue(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Body issue: Map<String, String?>
    ): Response<String>
}
