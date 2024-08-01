package com.synrgy7team4.feature_auth.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.synrgy7team4.feature_auth.data.Repository
import com.synrgy7team4.feature_auth.data.remote.response.ErrorResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val repository: Repository) : ViewModel() {
    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: MutableLiveData<Boolean> get() = _isLoading

    private var _isSuccessful = MutableLiveData<Boolean>()
    val isSuccessful: MutableLiveData<Boolean> get() = _isSuccessful

    private var _error = MutableLiveData<Exception>()
    val error: MutableLiveData<Exception> get() = _error

    fun login(email: String, password: String) =
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.login(email, password)
                _isSuccessful.value = true
            } catch (e: Exception) {
                _error.value = e
            } catch (e: HttpException) {
                val json = e.response()?.errorBody()?.string()
                val error = Gson().fromJson(json, ErrorResponse::class.java)
                _error.value = Exception(error.errors)
            } finally {
                _isLoading.value = false
            }
        }
}