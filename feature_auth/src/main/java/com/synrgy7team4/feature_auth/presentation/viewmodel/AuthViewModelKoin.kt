package com.synrgy7team4.feature_auth.presentation.viewmodel

import com.synrgy7team4.common.SharedPrefHelper
import com.synrgy7team4.feature_auth.presentation.login.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AuthViewModelKoin = module {
    single { SharedPrefHelper(androidContext()) }
    viewModel { LoginViewModel(get()) }
}