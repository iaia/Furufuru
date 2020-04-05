package dev.iaiabot.furufuru.di

import dev.iaiabot.furufuru.data.GITHUB_REPOSITORY
import dev.iaiabot.furufuru.data.GITHUB_REPOSITORY_OWNER
import dev.iaiabot.furufuru.data.repository.ContentRepository
import dev.iaiabot.furufuru.data.repository.ContentRepositoryImpl
import dev.iaiabot.furufuru.data.repository.IssueRepository
import dev.iaiabot.furufuru.data.repository.IssueRepositoryImpl
import dev.iaiabot.furufuru.feature.ui.issue.IssueViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (fileStr: String) ->
        IssueViewModel(get(), get(), fileStr)
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
}
