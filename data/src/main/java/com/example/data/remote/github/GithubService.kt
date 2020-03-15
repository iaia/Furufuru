package com.example.data.remote.github

import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface GithubService {
    @POST("/repos/:owner/:repo/issues")
    suspend fun postIssue(): Response
}
