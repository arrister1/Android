package com.synrgy7team4.feature_auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy7team4.domain.feature_auth.model.request.RegisterRequest
import com.synrgy7team4.domain.feature_auth.model.response.RegisterResponseDomain
import com.synrgy7team4.domain.feature_auth.usecase.HttpExceptionUseCase
import com.synrgy7team4.domain.feature_auth.usecase.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _registerResult = MutableLiveData<RegisterResponseDomain>()
    val registerResult: LiveData<RegisterResponseDomain> = _registerResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    fun register(
        email: String,
        hp: String,
        password: String,
        nik: String,
        name: String,
        date: String,
        pin: String,
        ektp_photo: String,
    ) = viewModelScope.launch {
        _isLoading.postValue(true)
        try {
            val registerRequest = RegisterRequest(
                email = email,
                password = password,
                name = name,
                no_hp = hp,
                no_ktp = nik,
                date_of_birth = date,
                ektp_photo = ektp_photo,
                pin = pin
            )
            val registerResponse = registerUseCase.register(registerRequest)
            _registerResult.postValue(registerResponse)
        } catch (e: HttpExceptionUseCase) {
            _error.postValue(e)
        } catch (e: Exception) {
            _error.postValue(e)
        } finally {
            _isLoading.postValue(false)
        }
    }
}