package com.main.myapplication.di

import com.main.myapplication.viewmodel.GithubViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        GithubViewModel(get())
    }
}