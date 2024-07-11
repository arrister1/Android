package com.synrgy7team4.bankingapps

import android.app.Application
import com.synrgy7team4.feature_auth_di.AuthKoin
import com.synrgy7team4.feature_auth_presentation.di.AuthViewModelKoin
import com.synrgy7team4.feature_mutasi_di.MutasiKoin
import com.synrgy7team4.feature_mutasi_presentation.di.MutasiViewModelKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@Application)
            modules(
                AuthKoin,
                AuthViewModelKoin,
                MutasiKoin,
                MutasiViewModelKoin
            )
        }
    }
}