package dev.iaiabot.furufuru.di

import android.app.Application
import android.util.LruCache
import dev.iaiabot.furufuru.data.repository.*
import dev.iaiabot.furufuru.feature.ui.issue.IssueViewModel
import dev.iaiabot.furufuru.feature.utils.screenshot.ScreenShotter
import dev.iaiabot.furufuru.usecase.IssueUseCase
import dev.iaiabot.furufuru.usecase.IssueUseCaseImpl
import dev.iaiabot.furufuru.usecase.UsernameUseCase
import dev.iaiabot.furufuru.usecase.UsernameUseCaseImpl
import dev.iaiabot.furufuru.util.FurufuruSettings
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal fun diModules() = listOf(
    viewModelModule,
    apiModule,
    repositoryModule,
    useCaseModule,
    utilModule,
)

private val viewModelModule = module {
    viewModel {
        IssueViewModel(androidContext() as Application, get(), get())
    }
}

private val apiModule = module {
    single<dev.iaiabot.furufuru.data.github.GithubService> { dev.iaiabot.furufuru.data.github.GithubApiClient.build() }
}

private val repositoryModule = module {
    single<IssueRepository> {
        IssueRepositoryImpl(
            get(),
            get()
        )
    }
    single<ContentRepository> {
        ContentRepositoryImpl(
            get(),
            get()
        )
    }
    single<ScreenshotRepository> {
        ScreenshotRepositoryImpl(get(named("ScreenShotCache")))
    }

    single<UserRepository> {
        UserRepositoryImpl(androidApplication())
    }
}

private val useCaseModule = module {
    single { ScreenShotter(get()) }
    single<UsernameUseCase> { UsernameUseCaseImpl(get()) }
    single<IssueUseCase> { IssueUseCaseImpl(get(), get(), get(), get()) }
}

private val utilModule = module {
    single { FurufuruSettings() }
    single(named("ScreenShotCache")) { LruCache<String, String>(1) }
}
