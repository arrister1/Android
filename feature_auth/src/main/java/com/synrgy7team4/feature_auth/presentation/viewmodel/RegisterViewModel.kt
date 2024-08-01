package com.synrgy7team4.feature_auth.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.synrgy7team4.feature_auth.data.remote.response.Data
import com.synrgy7team4.feature_auth.data.remote.response.ErrorResponse
import com.synrgy7team4.feature_auth.data.remote.response.RegistResponse
import com.synrgy7team4.feature_auth.data.remote.response.RegisterResponse
import com.synrgy7team4.feature_auth.data.remote.retrofit.RegisterBody
import com.synrgy7team4.feature_auth.data.repository.ImplementAuthRepository
import com.synrgy7team4.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RegisterViewModel(private val authRepository: AuthRepository): ViewModel() {


    private val _registerResult = MutableLiveData<Data?>()
    val registerResult: LiveData<Data?> = _registerResult

    private val _error = MutableLiveData<ErrorResponse>()
    val error: LiveData<ErrorResponse> = _error

    private val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())



//    fun registerUser(email: String, hp: String, password: String, ktp: String, name: String, pin: String) {
//        val date = Date()
//        val formattedDate = dateFormatter.format(date)
//
//        viewModelScope.launch {
//            try {
//                val registerBody = RegisterBody(email, hp, password, ktp, name, formattedDate, pin)
//                val response = authRepository.register(registerBody)
//                _registerResult.postValue(response)
//
//                Log.d("RegisterUser", "RegisterBody: $registerBody")
//
//            } catch (e: HttpException) {
//                val jsonInString = e.response()?.errorBody()?.string()
//                if (jsonInString != null) {
//                    val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
//                    val errorMessage = errorBody?.error ?: "Unknown error"
//                    _error.postValue(errorBody)
//                    Log.e("RegisterViewModel", "HTTP Error: $errorMessage")
//                } else {
//                    // Handle case where errorBody is null
//                    Log.e("RegisterViewModel", "HTTP Error: No error body")
//                    _error.postValue(ErrorResponse("Unknown error"))
//                }
//
//            } catch (e: Exception) {
//                // Handle other exceptions
//                Log.e("RegisterViewModel", "Error: ${e.message}")
//                _error.postValue(ErrorResponse("Unexpected error occurred"))
//            }
//        }
//    }


    fun registerUser(
        email: String,
        hp: String,
        password: String,
        ktp: String,
        name: String,
        date: String,
        pin: String,

//        ektp_photo: String,
    ) {
//        val date = Date()
//        val formattedDate = dateFormatter.format(date)


        viewModelScope.launch {

            try {
                val registerBody = RegisterBody(email, hp, password, ktp, name, date, pin)
                val response = authRepository.register(registerBody)
//                val registerBody = RegisterBody(email, hp, password, confirm_password, ktp, name, date, pin, confirm_pin, ektp_photo)
//                val response = authRepository.register(registerBody, context, uri)
                _registerResult.postValue(response)
                Log.d("RegisterUser", "RegisterBody: $registerBody")

            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, ErrorResponse::class.java)
                val errorMessage = errorBody.error
                _error.postValue(errorBody)
                Log.e("LoginViewModel", "HTTP Error: $errorMessage")


            }
        }

    }



    private fun parseErrorResponse(errorBody: String): ErrorResponse? {
        return try {
            // Assuming you're using Gson or a similar library
            val gson = Gson()
            gson.fromJson(errorBody, ErrorResponse::class.java)
        } catch (e: JsonSyntaxException) {
            null
        }
    }



}