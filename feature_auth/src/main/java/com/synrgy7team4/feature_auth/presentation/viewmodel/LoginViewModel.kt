package com.synrgy7team4.feature_auth.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.synrgy7team4.feature_auth.data.remote.request.LoginRequest
import com.synrgy7team4.feature_auth.data.remote.response.ErrorResponse
import com.synrgy7team4.feature_auth.data.remote.response.LoginResponse
import com.synrgy7team4.feature_auth.data.remote.response.RegisterResponse
import com.synrgy7team4.feature_auth.data.repository.ImplementAuthRepository
import com.synrgy7team4.feature_auth.domain.model.UserModel
import com.synrgy7team4.feature_auth.domain.repository.AuthRepository
import com.synrgy7team4.feature_auth.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: AuthRepository): ViewModel() {

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    private val _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful:LiveData<Boolean> = _isSuccessful

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            isLoading.value = true
            try {
                val loginRequest = LoginRequest(email, password)
                val loginResponse = repository.login(loginRequest)
                _isSuccessful.value = loginResponse.success
                Log.d("LoginViewModel", "LoginRequest: $loginRequest")


            } catch (throwable: Throwable) {
                if (throwable is HttpException) {
                    val json = throwable.response()?.errorBody()?.string()
                    val error = Gson().fromJson(json, ErrorResponse::class.java)
                    _error.value = error.message
                    Log.e("LoginViewModel", "HTTP Error: ${error.message}")
                } else {
                    _error.value = throwable.message
                    Log.e("LoginViewModel", "Error: ${throwable.message}")

                }
            } finally {
                _isLoading.value = false

            }
        }

    }







}