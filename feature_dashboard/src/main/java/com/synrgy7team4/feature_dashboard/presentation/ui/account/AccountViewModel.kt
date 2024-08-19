package com.synrgy7team4.feature_dashboard.presentation.ui.account

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy7team4.domain.feature_auth.usecase.TokenUseCase
import kotlinx.coroutines.launch

class AccountViewModel(
    private val tokenUseCase: TokenUseCase
) : ViewModel() {
    private val _isDeleteTokenSuccessful = MutableLiveData<Boolean>()
    val isDeleteTokenSuccessful: LiveData<Boolean> = _isDeleteTokenSuccessful

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    fun deleteJwtToken() =
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                tokenUseCase.deleteJwtToken()
                _isDeleteTokenSuccessful.postValue(true)
            } catch (e: Exception) {
                _error.postValue(e)
                _isDeleteTokenSuccessful.postValue(false)
            } finally {
                _isLoading.postValue(false)
            }
        }
}