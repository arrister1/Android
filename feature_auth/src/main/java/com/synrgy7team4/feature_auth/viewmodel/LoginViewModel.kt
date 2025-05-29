package com.synrgy7team4.feature_auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy7team4.common.TokenHandler
import com.synrgy7team4.common.UserHandler
import com.synrgy7team4.domain.feature_auth.model.request.LoginRequest
import com.synrgy7team4.domain.feature_auth.model.response.UserGetResponseDomain
import com.synrgy7team4.domain.feature_auth.usecase.HttpExceptionUseCase
import com.synrgy7team4.domain.feature_auth.usecase.LoginUseCase
import com.synrgy7team4.domain.feature_auth.usecase.UserUseCase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val userUseCase: UserUseCase,
    private val tokenHandler: TokenHandler,
    private val userHandler: UserHandler
) : ViewModel() {
    private val _tokens = MutableLiveData<Pair<String, String>>()
    private val _userData = MutableLiveData<UserGetResponseDomain>()

    private val _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful: LiveData<Boolean> = _isSuccessful

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    fun login(email: String, password: String) = viewModelScope.launch {
        _isLoading.value = true
        try {
            val loginRequest = LoginRequest(email, password)
            val loginResponse = loginUseCase.login(loginRequest)
            _tokens.value = Pair(loginResponse.data.jwtToken, loginResponse.data.refreshToken)

            val userDataGetResponse = userUseCase.getUserData("Bearer ${_tokens.value!!.first}")
            _userData.value = userDataGetResponse

            _isSuccessful.value = true
        } catch (e: HttpExceptionUseCase) {
            _error.value = e
            _isSuccessful.value = false
        } catch (e: Exception) {
            _error.value = e
            _isSuccessful.value = false
        } finally {
            _isLoading.value = false
        }
    }

    fun saveTokens(): Deferred<Unit> = viewModelScope.async {
        _isLoading.value = true
        try {
            tokenHandler.saveTokens(
                jwtToken = _tokens.value!!.first,
                refreshToken = _tokens.value!!.second
            )
        } catch (e: Exception) {
            _error.value = e
        } finally {
            _isLoading.value = false
        }
    }

    fun saveUserData(): Deferred<Unit> = viewModelScope.async {
        _isLoading.value = true
        try {
            userHandler.apply {
                saveUserName(_userData.value?.data!!.name)
                saveUserEmail(_userData.value?.data!!.email)
                saveUserPhoneNumber(_userData.value?.data!!.noHp)
                saveAccountNumber(_userData.value?.data!!.accountNumber)
                saveAccountPin(_userData.value?.data!!.accountPin)
                saveDateOfBirth(_userData.value?.data!!.dateOfBirth)
                saveKtpNumber(_userData.value?.data!!.noKtp)
                saveKtpPhoto(_userData.value?.data!!.ektpPhoto)
            }
        } catch (e: Exception) {
            _error.value = e
        } finally {
            _isLoading.value = false
        }
    }
}