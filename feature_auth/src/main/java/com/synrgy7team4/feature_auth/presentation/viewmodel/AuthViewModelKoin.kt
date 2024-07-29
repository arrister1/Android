package com.synrgy7team4.feature_auth.presentation.viewmodel

import com.synrgy7team4.feature_auth.presentation.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AuthViewModelKoin = module {
    viewModel { LoginViewModel(get()) }
}