package com.jer.di

import android.app.Application
import com.synrgy7team4.feature_auth.di.AuthKoin
import com.synrgy7team4.feature_auth.di.Module

import com.synrgy7team4.feature_auth.presentation.viewmodel.AuthViewModelKoin
import com.synrgy7team4.feature_mutasi.di.MutasiKoin
import com.synrgy7team4.feature_mutasi.presentation.viewmodel.MutasiViewModelKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    lateinit var module: Module
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate() {
        super.onCreate()

        module = Module(this)
        viewModelFactory = ViewModelFactory(module)

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                AuthKoin,
                AuthViewModelKoin,
                MutasiKoin,
                MutasiViewModelKoin
            )
        }
    }
}