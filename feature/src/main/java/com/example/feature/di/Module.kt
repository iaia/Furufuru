package com.example.feature.di

import com.example.feature.ui.issue.IssueViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (fileStr: String) ->
        IssueViewModel(get(), get(), fileStr)
    }
}
