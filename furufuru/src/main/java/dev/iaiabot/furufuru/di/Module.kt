package dev.iaiabot.furufuru.di

import android.app.Application
import dev.iaiabot.furufuru.data.GITHUB_REPOSITORY
import dev.iaiabot.furufuru.data.GITHUB_REPOSITORY_OWNER
import dev.iaiabot.furufuru.data.repository.*
import dev.iaiabot.furufuru.feature.ui.issue.IssueViewModel
import dev.iaiabot.furufuru.feature.utils.screenshot.ScreenShotter
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        IssueViewModel(androidContext() as Application, get(), get(), get())
    }
}

val apiModule = module {
    single<dev.iaiabot.furufuru.data.github.GithubService> { dev.iaiabot.furufuru.data.github.GithubApiClient.build() }
}

val repositoryModule = module {
    single<IssueRepository> {
        IssueRepositoryImpl(
            GITHUB_REPOSITORY_OWNER,
            GITHUB_REPOSITORY,
            get()
        )
    }
    single<ContentRepository> {
        ContentRepositoryImpl(
            GITHUB_REPOSITORY_OWNER,
            GITHUB_REPOSITORY,
            get()
        )
    }
    single<ScreenshotRepository> {
        ScreenshotRepositoryImpl()
    }
}

val useCaseModule = module {
    single { ScreenShotter(get()) }
}
