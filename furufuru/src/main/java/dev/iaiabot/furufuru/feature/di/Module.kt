package dev.iaiabot.furufuru.feature.di

import dev.iaiabot.furufuru.feature.ui.issue.IssueViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (fileStr: String) ->
        IssueViewModel(get(), get(), fileStr)
    }
}
