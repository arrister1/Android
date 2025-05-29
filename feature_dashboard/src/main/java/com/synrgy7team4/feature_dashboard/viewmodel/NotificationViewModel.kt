package com.synrgy7team4.feature_dashboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy7team4.common.TokenHandler
import com.synrgy7team4.domain.feature_dashboard.model.response.NotificationGetResponseItemDomain
import com.synrgy7team4.domain.feature_dashboard.usecase.DashboardUseCase
import com.synrgy7team4.domain.feature_dashboard.usecase.HttpExceptionUseCase
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val dashboardUseCase: DashboardUseCase,
    private val tokenHandler: TokenHandler,
): ViewModel() {
    private val _notificationGetResponse = MutableLiveData<List<NotificationGetResponseItemDomain>>()
    val notificationGetResponse: LiveData<List<NotificationGetResponseItemDomain>> = _notificationGetResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    fun getNotification() = viewModelScope.launch {
        _isLoading.value = true
        try {
            if (tokenHandler.isTokenExpired()) {
                tokenHandler.handlingTokenExpire()
            } else {
                val jwtToken = tokenHandler.loadJwtToken() ?: throw Exception("JWT token tidak tersedia")
                val getNotification = dashboardUseCase.getNotification("Bearer $jwtToken")
                _notificationGetResponse.value = getNotification
            }
        } catch (e: HttpExceptionUseCase) {
            _error.value = e
        } catch (e: Exception) {
            _error.value = e
        } finally {
            _isLoading.value = false
        }
    }
}