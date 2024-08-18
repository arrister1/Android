package com.synrgy7team4.bankingapps.di

import androidx.appcompat.app.AppCompatActivity
import com.synrgy7team4.bankingapps.splashScreen.SplashViewModel
import com.synrgy7team4.common.NavigationHandler
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appKoin = module {
    single<NavigationHandler> { get<AppCompatActivity>() as NavigationHandler }
    viewModel { SplashViewModel(get()) }
}