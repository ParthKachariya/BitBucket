package com.main.myapplication.di

import com.main.myapplication.repository.GithubRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        GithubRepository(get())
    }
}