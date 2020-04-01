package dev.iaiabot.furufuru.data.remote.github

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.iaiabot.furufuru.data.BuildConfig
import dev.iaiabot.furufuru.data.GITHUB_API_TOKEN
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit

object GithubApiClient {
    fun build(): GithubService {
        return buildRetrofit().create(GithubService::class.java)
    }

    private fun buildRetrofit(): Retrofit {
        val contentType = MediaType.parse("application/json")!!
        val json = Json.nonstrict
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "token $GITHUB_API_TOKEN")
                .build()
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
