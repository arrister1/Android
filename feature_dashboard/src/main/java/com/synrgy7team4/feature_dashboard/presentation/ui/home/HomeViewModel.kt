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
    private val _userResponse = MutableLiveData<UserResponse?>()
    val userResponse: LiveData<UserResponse?> get() = _userResponse

    private val _data = MutableLiveData<Double>()
    val data: LiveData<Double> = _data

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String> = _userName

    private val _accountNumber = MutableLiveData<String>()
    val accountNumber: LiveData<String> = _accountNumber

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

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

    fun getBalance(accountNumber: String) {
        viewModelScope.launch {
            try {
                val response = repository.getBalance(accountNumber)
                if (response.success) {
                    val balanceResponse = response.data
                    _data.postValue(balanceResponse)
                    Log.d("HomeViewModel", "Balance Response: $response")

                } else {
                    _error.postValue("Error: ${response.message}")
                }
//                _data.postValue(response)
            } catch (e: HttpException){
                _error.postValue("An unexpected error occurred: ${e.message}")

//                val errorBody = e.response()?.body()?.toString()
//                _error.postValue("HTTP Error: ${e.message()} - $errorBody")
            }

        }
    }

}


//    private val _text = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
//    }
//    val text: LiveData<String> = _text
//
//

//
//    private val _error = MutableLiveData<String>()
//    val error: LiveData<String> = _error
//
//
//    fun fectData(token: String, accountNumber: String ) {
//
//        viewModelScope.launch {
//
//            try {
//                val response = provideService().getBalance("Bearer $token", accountNumber)
//                _data.postValue(response)
//
//            } catch (e: HttpException){
//                val errorBody = e.response()?.body()?.toString()
//                _error.postValue("HTTP Error: ${e.message()} - $errorBody")
//            } catch (e: Exception) {
//                _error.postValue("An unexpected error occurred: ${e.message}")
//            }
//
//
//
//        }
//
//    }






//class HomeViewModel : ViewModel() {
//
//    private val _text = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
//    }
//    val text: LiveData<String> = _text
//
//
//    private val _data = MutableLiveData<BalanceResponse>()
//    val data: LiveData<BalanceResponse> = _data
//
//    private val _error = MutableLiveData<String>()
//    val error: LiveData<String> = _error
//
//
//    fun fectData(token: String, accountNumber: String ) {
//
//        viewModelScope.launch {
//
//            try {
//                val response = provideService().getBalance("Bearer $token", accountNumber)
//                _data.postValue(response)
//
//            } catch (e: HttpException){
//                val errorBody = e.response()?.body()?.toString()
//                _error.postValue("HTTP Error: ${e.message()} - $errorBody")
//            } catch (e: Exception) {
//                _error.postValue("An unexpected error occurred: ${e.message}")
//            }
//
//
//
//        }
//
//    }
//
//
//
//}