package com.synrgy7team4.feature_auth.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.synrgy7team4.feature_auth.data.remote.response.ErrorResponse
import com.synrgy7team4.feature_auth.data.remote.response.LoginResponse
import com.synrgy7team4.feature_auth.data.remote.response.RegisterResponse
import com.synrgy7team4.feature_auth.domain.model.UserModel
import com.synrgy7team4.feature_auth.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val loginUseCase: LoginUseCase): ViewModel() {

//    private val _loginResult = MutableLiveData<LoginResponse>()
//    val loginResult: LiveData<LoginResponse> = _loginResult

    private val _works = MutableLiveData<Boolean>()
    val works: LiveData<Boolean> = _works

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loginUser(email: String, password: String) {

        viewModelScope.launch {
            try {
                loginUseCase.login(email, password)
                _works.value = true


            } catch (throwable: Throwable) {
                if (throwable is HttpException) {
                    val json = throwable.response()?.errorBody()?.string()
                    val error = Gson().fromJson(json, ErrorResponse::class.java)
                    _error.value = error.error
                } else {
                    _error.value = throwable.message
                }
            }
        }

    }





}