package dev.iaiabot.furufuru.di

import android.content.Context
import android.content.SharedPreferences
import android.util.LruCache
import dev.iaiabot.furufuru.data.entity.ScreenShot
import dev.iaiabot.furufuru.data.entity.User
import dev.iaiabot.furufuru.data.local.ScreenshotDataSource
import dev.iaiabot.furufuru.data.local.UserDataSource
import dev.iaiabot.furufuru.feature.ui.issue.IssueViewModel
import dev.iaiabot.furufuru.feature.ui.issue.IssueViewModelImpl
import dev.iaiabot.furufuru.feature.utils.screenshot.ScreenShotter
import dev.iaiabot.furufuru.repository.*
import dev.iaiabot.furufuru.usecase.GetScreenShotUseCase
import dev.iaiabot.furufuru.usecase.GetScreenShotUseCaseImpl
import dev.iaiabot.furufuru.usecase.PostIssueUseCase
import dev.iaiabot.furufuru.usecase.PostIssueUseCaseImpl
import dev.iaiabot.furufuru.usecase.user.LoadUserNameUseCase
import dev.iaiabot.furufuru.usecase.user.LoadUserNameUseCaseImpl
import dev.iaiabot.furufuru.usecase.user.SaveUsernameUseCase
import dev.iaiabot.furufuru.usecase.user.SaveUsernameUseCaseImpl
import dev.iaiabot.furufuru.util.GithubSettings
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal fun diModules() = listOf(
    viewModelModule,
    apiModule,
    repositoryModule,
    useCaseModule,
    utilModule,
    dataModule,
    androidModule,
)

private val viewModelModule = module {
    viewModel<IssueViewModel> {
        IssueViewModelImpl(get(), get(), get(), get())
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
        ScreenshotRepositoryImpl(get())
    }

    single<UserRepository> {
        UserRepositoryImpl(get())
    }
}

private val useCaseModule = module {
    single<SaveUsernameUseCase> { SaveUsernameUseCaseImpl(get()) }
    single<LoadUserNameUseCase> { LoadUserNameUseCaseImpl(get()) }
    single<PostIssueUseCase> { PostIssueUseCaseImpl(get(), get(), get(), get(), get()) }
    single<GetScreenShotUseCase> { GetScreenShotUseCaseImpl(get()) }
}

private val utilModule = module {
    single { ScreenShotter(get()) }
    single { GithubSettings() }
    single(named("ScreenShotCache")) { LruCache<String, String>(1) }
}

private val dataModule = module {
    single<User> { UserDataSource(get()) }
    single<ScreenShot> { ScreenshotDataSource(get(named("ScreenShotCache"))) }
}

private val androidModule = module {
    single<SharedPreferences> {
        androidApplication().getSharedPreferences(
            "furufuru",
            Context.MODE_PRIVATE
        )
    }
}
