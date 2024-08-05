package com.synrgy7team4.bankingapps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.synrgy7team4.feature_auth.di.Module
import com.synrgy7team4.feature_auth.presentation.viewmodel.LoginViewModel
import com.synrgy7team4.feature_auth.presentation.viewmodel.RegisterViewModel
import com.synrgy7team4.feature_dashboard.presentation.ui.home.HomeViewModel

class ViewModelFactory(private val module: Module): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {

            RegisterViewModel::class.java-> RegisterViewModel(authRepository = module.authRepository) as T
            LoginViewModel::class.java -> LoginViewModel(loginUseCase = module.loginUseCase) as T
            HomeViewModel::class.java-> HomeViewModel(authRepository = module.authRepository) as T


            else -> {
                throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
            }
        }


    }
}