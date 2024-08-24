package com.synrgy7team4.bankingapps

import android.app.Application
import com.synrgy7team4.bankingapps.di.appKoin
import com.synrgy7team4.di.feature_auth.authKoin
import com.synrgy7team4.di.feature_dashboard.dashboardKoin
import com.synrgy7team4.di.feature_mutasi.mutasiKoin
import com.synrgy7team4.feature_auth.viewmodel.authViewModelKoin
import com.synrgy7team4.feature_dashboard.viewmodel.dashboardViewModelKoin
import com.synrgy7team4.feature_mutasi.viewmodel.mutasiViewModelKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(
                appKoin,
                authKoin,
                authViewModelKoin,
                dashboardKoin,
                dashboardViewModelKoin,
                mutasiKoin,
                mutasiViewModelKoin,
            )
        }
    }
}