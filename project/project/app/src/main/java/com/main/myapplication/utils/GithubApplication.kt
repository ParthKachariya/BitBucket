package com.main.myapplication.utils

import android.app.Application
import com.main.myapplication.di.networkModule
import com.main.myapplication.di.repositoryModule
import com.main.myapplication.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GithubApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GithubApplication)
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }
}