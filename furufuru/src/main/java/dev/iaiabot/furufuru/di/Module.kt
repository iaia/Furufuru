package dev.iaiabot.furufuru.di

import android.app.Application
import dev.iaiabot.furufuru.data.repository.*
import dev.iaiabot.furufuru.feature.ui.issue.IssueViewModel
import dev.iaiabot.furufuru.feature.utils.screenshot.ScreenShotter
import dev.iaiabot.furufuru.util.FurufuruSettings
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun diModules() = listOf(
    viewModelModule,
    apiModule,
    repositoryModule,
    useCaseModule,
    utilModule,
)

private val viewModelModule = module {
    viewModel {
        IssueViewModel(androidContext() as Application, get(), get(), get(), get(), get())
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
        ScreenshotRepositoryImpl()
    }

    single<UserRepository> {
        UserRepositoryImpl()
    }
}

private val useCaseModule = module {
    single { ScreenShotter(get()) }
}

private val utilModule = module {
    single { FurufuruSettings() }
}
