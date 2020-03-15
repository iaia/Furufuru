package com.example.feature.di

import com.example.feature.IssueViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (filePath : String) -> IssueViewModel(get(), filePath) }
}
