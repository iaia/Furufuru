package com.example.feature.di

import com.example.feature.MainViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.koinApplication
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}
