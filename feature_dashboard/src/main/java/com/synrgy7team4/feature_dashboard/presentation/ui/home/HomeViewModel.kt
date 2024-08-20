package com.synrgy7team4.feature_dashboard.presentation.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy7team4.feature_dashboard.data.Repository
import com.synrgy7team4.feature_dashboard.data.remote.retrofit.provideService
import com.synrgy7team4.feature_dashboard.data.remote.response.BalanceResponse
import com.synrgy7team4.feature_dashboard.data.remote.response.user.UserResponse
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel(private val repository: Repository) : ViewModel() {
    private val TAG = "HomeViewModel"

    private val _userResponse = MutableLiveData<UserResponse?>()
    val userResponse: LiveData<UserResponse?> get() = _userResponse

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _accountNumber = MutableLiveData<String>()
    val accountNumber: LiveData<String> = _accountNumber

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    //BALANCE
    private val _balance = MutableLiveData<Double>()
    val balance:LiveData<Double> get() = _balance


    fun fetchUserData(token: String) {
        viewModelScope.launch {
            try {
                val response = repository.getUserData(token)
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    _userResponse.postValue(userResponse)
                    userResponse?.data?.let { userData ->
                        _userName.postValue(userData.name)
                        _accountNumber.postValue(userData.accountNumber)
                    }
                } else {
                    _error.postValue("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _error.postValue("An unexpected error occurred: ${e.message}")
            }
        }
    }

    fun fetchBalance(token: String, accountNumber: String){
        viewModelScope.launch {
            try {
                val response = repository.getBalance(token, accountNumber)
                if (response.success){
                    _balance.postValue(response.data)
                    Log.d(TAG, "Balance fetched successfully: ${response.data}") // Log saat balance berhasil diambil

                } else {
                    _error.postValue("Error: ${response.message}")
                }
            } catch (e: Exception){
                _error.postValue("Unexpected error ${e.message}")
            }
        }
    }



}
