package com.synrgy7team4.feature_dashboard.viewmodel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dashboardViewModelKoin = module {
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { AccountViewModel(get(), get()) }
    viewModel { NotificationViewModel(get(), get()) }
}