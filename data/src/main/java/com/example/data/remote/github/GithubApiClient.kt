package com.example.data.remote.github

import android.util.Log
import com.example.data.BuildConfig
import com.example.data.entity.Issue
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.*
import retrofit2.Retrofit
import java.io.IOException

object GithubApiClient {
    fun build(): GithubService {
        return buildRetrofit().create(GithubService::class.java)
    }

    private fun buildRetrofit(): Retrofit {
        val contentType = MediaType.parse("application/json")!!
        val json = Json.nonstrict
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "token ${BuildConfig.GITHUB_API_TOKEN}")
                .build()
            Log.d("koko", "${newRequest.method()} ${newRequest.url()}")
            Log.d("koko", "${newRequest.header("Authorization")}")
            chain.proceed(newRequest)
        }.build()
        return Retrofit.Builder().run {
            client(client)
            addConverterFactory(json.asConverterFactory(contentType))
            baseUrl(BuildConfig.GITHUB_API_URL)
            build()
        }
    }
}
