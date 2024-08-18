package com.synrgy7team4.feature_auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy7team4.domain.feature_auth.model.request.LoginRequest
import com.synrgy7team4.domain.feature_auth.usecase.HttpExceptionUseCase
import com.synrgy7team4.domain.feature_auth.usecase.LoginUseCase
import com.synrgy7team4.domain.feature_auth.usecase.TokenUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val tokenUseCase: TokenUseCase
) : ViewModel() {
    private val _tokens = MutableLiveData<Pair<String, String>>()
    val tokens: LiveData<Pair<String, String>> = _tokens

    private val _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful: LiveData<Boolean> = _isSuccessful

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    fun login(email: String, password: String) =
        viewModelScope.launch {
            _isLoading.postValue(true)
            try {
                val loginRequest = LoginRequest(email, password)
                val loginResponse = loginUseCase.login(loginRequest)
                _tokens.postValue(
                    Pair(loginResponse.data.jwtToken, loginResponse.data.refreshToken)
                )
                _isSuccessful.postValue(true)
            } catch (e: HttpExceptionUseCase) {
                _error.postValue(e)
            } catch (e: Exception) {
                _error.postValue(e)
            } finally {
                _isSuccessful.postValue(false)
                _isLoading.postValue(false)
            }
        }

    fun saveTokens(jwtToken: String, refreshToken: String) =
        viewModelScope.launch {
            try {
                tokenUseCase.saveTokens(jwtToken, refreshToken)
            } catch (e: Exception) {
                _error.postValue(e)
            }
        }
}