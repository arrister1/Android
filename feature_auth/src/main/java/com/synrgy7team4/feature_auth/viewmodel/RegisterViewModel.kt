package com.synrgy7team4.feature_auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy7team4.domain.feature_auth.model.request.EmailCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.KtpNumberCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.PhoneNumberCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.RegisterRequest
import com.synrgy7team4.domain.feature_auth.model.response.RegisterResponseDomain
import com.synrgy7team4.domain.feature_auth.usecase.HttpExceptionUseCase
import com.synrgy7team4.domain.feature_auth.usecase.RegisterUseCase
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    private val _isEmailAvailable = MutableLiveData<Boolean>()
    val isEmailAvailable: LiveData<Boolean> = _isEmailAvailable

    private val _isPhoneNumberAvailable = MutableLiveData<Boolean>()
    val isPhoneNumberAvailable: LiveData<Boolean> = _isPhoneNumberAvailable

    private val _isKtpNumberAvailable = MutableLiveData<Boolean>()
    val isKtpNumberAvailable: LiveData<Boolean> = _isKtpNumberAvailable

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
            registerUseCase.register(registerRequest)
        } catch (e: HttpExceptionUseCase) {
            _error.postValue(e)
        } catch (e: Exception) {
            _error.postValue(e)
        } finally {
            _isLoading.postValue(false)
        }
    }

    fun checkEmailAvailability(email: String) = viewModelScope.launch {
        _isLoading.postValue(true)
        try {
            val emailCheckRequest = EmailCheckRequest(email)
            val emailCheckResponse = registerUseCase.checkEmailAvailability(emailCheckRequest)
            _isEmailAvailable.postValue(emailCheckResponse.success)
        } catch (e: HttpExceptionUseCase) {
            _error.postValue(e)
        } catch (e: Exception) {
            _error.postValue(e)
        } finally {
            _isLoading.postValue(false)
        }
    }

    fun checkPhoneNumberAvailability(phoneNumber: String) = viewModelScope.launch {
        _isLoading.postValue(true)
        try {
            val phoneNumberCheckRequest = PhoneNumberCheckRequest(phoneNumber)
            val phoneNumberCheckResponse = registerUseCase.checkPhoneNumberAvailability(phoneNumberCheckRequest)
            _isPhoneNumberAvailable.postValue(phoneNumberCheckResponse.success)
        } catch (e: HttpExceptionUseCase) {
            _error.postValue(e)
        } catch (e: Exception) {
            _error.postValue(e)
        } finally {
            _isLoading.postValue(false)
        }
    }

    fun checkKtpNumberAvailability(ktpNumber: String) = viewModelScope.launch {
        _isLoading.postValue(true)
        try {
            val ktpNumberCheckRequest = KtpNumberCheckRequest(ktpNumber)
            val ktpNumberCheckResponse = registerUseCase.checkKtpNumberAvailability(ktpNumberCheckRequest)
            _isKtpNumberAvailable.postValue(ktpNumberCheckResponse.success)
        } catch (e: HttpExceptionUseCase) {
            _error.postValue(e)
        } catch (e: Exception) {
            _error.postValue(e)
        } finally {
            _isLoading.postValue(false)
        }
    }
}