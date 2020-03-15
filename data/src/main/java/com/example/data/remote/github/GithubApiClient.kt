package com.example.data.remote.github

import android.content.Context
import com.example.data.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


object GithubApiClient {
    fun build(): GithubService {
        return buildRetrofit().create(GithubService::class.java)
    }

    private fun buildRetrofit(): Retrofit {
        val contentType = MediaType.parse("application/json")!!
        val json = Json.nonstrict
        return Retrofit.Builder().run {
            addConverterFactory(json.asConverterFactory(contentType))
            baseUrl(BuildConfig.GITHUB_API_URL)
            build()
        }
    }
}
