package com.synrgy7team4.bankingapps.di

import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.synrgy7team4.bankingapps.dataStore
import com.synrgy7team4.bankingapps.splashScreen.SplashViewModel
import com.synrgy7team4.common.NavigationHandler
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appKoin = module {
    single<NavigationHandler> { get<AppCompatActivity>() as NavigationHandler }
    single<DataStore<Preferences>> { androidContext().dataStore }

    viewModel { SplashViewModel(get(), get()) }
}