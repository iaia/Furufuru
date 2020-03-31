package dev.iaiabot.furufuru.data.di

import dev.iaiabot.furufuru.data.GITHUB_REPOSITORY
import dev.iaiabot.furufuru.data.GITHUB_REPOSITORY_OWNER
import dev.iaiabot.furufuru.data.remote.github.GithubApiClient
import dev.iaiabot.furufuru.data.remote.github.GithubService
import dev.iaiabot.furufuru.data.repository.ContentRepository
import dev.iaiabot.furufuru.data.repository.ContentRepositoryImpl
import dev.iaiabot.furufuru.data.repository.IssueRepository
import dev.iaiabot.furufuru.data.repository.IssueRepositoryImpl
import org.koin.dsl.module

val apiModule = module {
    single<GithubService> { GithubApiClient.build() }
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
