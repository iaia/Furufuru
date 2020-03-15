package com.example.data.di

import com.example.data.remote.github.GithubApiClient
import com.example.data.remote.github.GithubService
import com.example.data.repository.IssueRepository
import com.example.data.repository.IssueRepositoryImpl
import org.koin.dsl.module

val apiModule = module {
    single<GithubService> { GithubApiClient.build() }
}

val repositoryModule = module {
    single<IssueRepository> { IssueRepositoryImpl(get()) }
}
