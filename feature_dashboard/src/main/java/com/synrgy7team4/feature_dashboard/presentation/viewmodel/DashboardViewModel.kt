package com.synrgy7team4.feature_dashboard.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.synrgy7team4.feature_auth.domain.repository.AuthRepository


class DashboardViewModel(private val authRepository: AuthRepository): ViewModel() {
    val userName: LiveData<String?> = liveData {
        authRepository.loadSession().collect { dataX ->
            emit(dataX.name)
        }
    }
}