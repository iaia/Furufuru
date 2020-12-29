package dev.iaiabot.furufuru.data.github

import android.util.Log
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dev.iaiabot.furufuru.BuildConfig
import dev.iaiabot.furufuru.util.GithubSettings
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import org.koin.java.KoinJavaComponent.inject
import retrofit2.Retrofit

internal object GithubApiClient {
    private val GITHUB_SETTINGS: GithubSettings by inject(GithubSettings::class.java)

    fun build(): GithubService {
        return buildRetrofit()
            .create(GithubService::class.java)
    }

    private fun buildRetrofit(): Retrofit {
        val contentType = MediaType.parse("application/json")!!
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val request = chain.request()
            val buffer = okio.Buffer()
            request.body()?.writeTo(buffer)
            Log.d("Furufuru request body", buffer.readUtf8())

            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "token ${GITHUB_SETTINGS.githubApiToken}")
                .build()
            chain.proceed(newRequest)
        }.build()
        return Retrofit.Builder().run {
            client(client)
            addConverterFactory(
                Json {
                    encodeDefaults = false
                    ignoreUnknownKeys = true
                    isLenient = true
                }.asConverterFactory(contentType))
            baseUrl(BuildConfig.GITHUB_API_URL)
            build()
        }
    }
}
