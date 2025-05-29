package com.synrgy7team4.feature_auth.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy7team4.domain.feature_auth.model.request.EmailCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.ForgotPasswordRequest
import com.synrgy7team4.domain.feature_auth.model.request.KtpNumberCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.PhoneNumberCheckRequest
import com.synrgy7team4.domain.feature_auth.model.request.RegisterRequest
import com.synrgy7team4.domain.feature_auth.model.request.SendOtpRequest
import com.synrgy7team4.domain.feature_auth.model.request.VerifyOtpRequest
import com.synrgy7team4.domain.feature_auth.model.response.OtpResponseDomain
import com.synrgy7team4.domain.feature_auth.model.response.RegisterResponseDomain
import com.synrgy7team4.domain.feature_auth.usecase.HttpExceptionUseCase
import com.synrgy7team4.domain.feature_auth.usecase.RegisterUseCase
import com.synrgy7team4.feature_auth.data.remote.response.ForgotPasswordResponseDomain
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {


    private val _registerResult = MutableLiveData<RegisterResponseDomain>()
    val registerResult: LiveData<RegisterResponseDomain> = _registerResult

    private val _isEmailAvailable = MutableLiveData<Boolean>()
    val isEmailAvailable: LiveData<Boolean> = _isEmailAvailable

    private val _isPhoneNumberAvailable = MutableLiveData<Boolean>()
    val isPhoneNumberAvailable: LiveData<Boolean> = _isPhoneNumberAvailable

    private val _isKtpNumberAvailable = MutableLiveData<Boolean>()
    val isKtpNumberAvailable: LiveData<Boolean> = _isKtpNumberAvailable

    private val _sendOtpResult = MutableLiveData<OtpResponseDomain>()
    val sendOtpResult: LiveData<OtpResponseDomain> = _sendOtpResult

    private val _verifyOtpResult = MutableLiveData<OtpResponseDomain>()
    val verifyOtpResult: LiveData<OtpResponseDomain> = _verifyOtpResult

    private val _sendForgetPassResult = MutableLiveData<ForgotPasswordResponseDomain>()
    val sendForgetPassResult: LiveData<ForgotPasswordResponseDomain> = _sendForgetPassResult

    private val _validateForgetPassResult = MutableLiveData<ForgotPasswordResponseDomain>()
    val validateForgetPassResult: LiveData<ForgotPasswordResponseDomain> = _validateForgetPassResult

    private val _setNewPassResult = MutableLiveData<ForgotPasswordResponseDomain>()
    val setNewPassResult: LiveData<ForgotPasswordResponseDomain> = _setNewPassResult

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    fun sendOtp(email: String, hp: String) = viewModelScope.launch {
        _isLoading.value = true
        try {
            val sendOtpRequest = SendOtpRequest(email, hp)
            val otpResponse = registerUseCase.sendOtp(sendOtpRequest)
            _sendOtpResult.postValue(otpResponse)
        } catch (e: HttpExceptionUseCase) {
            _error.value = e
        } catch (e: Exception) {
            _error.value = e
        } finally {
            _isLoading.value = false
        }
    }

    fun verifyOtp(email: String, otp: String) = viewModelScope.launch {
        _isLoading.value = true
        try {
            val verifyOtpRequest = VerifyOtpRequest(email, otp)
            val otpResponse = registerUseCase.verifyOtp(verifyOtpRequest)
            _verifyOtpResult.value = otpResponse
        } catch (e: HttpExceptionUseCase) {
            _error.value = e
        } catch (e: Exception) {
            _error.value = e
        } finally {
            _isLoading.value = false
        }
    }

    fun register(
        email: String,
        hp: String,
        password: String,
        nik: String,
        name: String,
        date: String,
        pin: String,
        ektp_photo: String,
        otp: String,
        is_verified: Boolean
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
                ektp_photo = "data:image/png:base64,iVBORwBKGgoAAA",
                pin = pin,
                otp = otp,
                is_verified = is_verified
            )
            registerUseCase.register(registerRequest)
            val response = registerUseCase.register(registerRequest)

            Log.d("RegisterViewModel", "User registered: ${response.success}, ${response.message}")
        } catch (e: HttpExceptionUseCase) {
            _error.postValue(e)
//            Log.e("RegisterViewModel", "Error registering user: ${e.message}")
        } catch (e: Exception) {
            _error.postValue(e)
//            Log.e("RegisterViewModel", "Error registering user: ${e.message}")
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
            val phoneNumberCheckResponse =
                registerUseCase.checkPhoneNumberAvailability(phoneNumberCheckRequest)
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
            val ktpNumberCheckResponse =
                registerUseCase.checkKtpNumberAvailability(ktpNumberCheckRequest)
            _isKtpNumberAvailable.postValue(ktpNumberCheckResponse.success)
        } catch (e: HttpExceptionUseCase) {
            _error.postValue(e)
        } catch (e: Exception) {
            _error.postValue(e)
        } finally {
            _isLoading.postValue(false)
        }
    }

    fun sendForgetPass(email: String) = viewModelScope.launch {
        _isLoading.value = true
        try {
            val sendForgetPass = ForgotPasswordRequest(email)
            val forgetPassResponse = registerUseCase.sendForgetPass(sendForgetPass)
            _sendForgetPassResult.postValue(forgetPassResponse)
        } catch (e: HttpExceptionUseCase) {
            _error.value = e
        } catch (e: Exception) {
            _error.value = e
        } finally {
            _isLoading.value = false
        }
    }

    fun validateForgetPass(email: String, otp: String) = viewModelScope.launch {
        _isLoading.value = true
        try {
            val validateForgetPass = ForgotPasswordRequest(email, otp)
            val validateForgetPassResponse = registerUseCase.validateForgetPass(validateForgetPass)
            _sendForgetPassResult.postValue(validateForgetPassResponse)
        } catch (e: HttpExceptionUseCase) {
            _error.value = e
        } catch (e: Exception) {
            _error.value = e
        } finally {
            _isLoading.value = false
        }
    }

    fun setNewPass(email: String, otp: String, newPass:String) = viewModelScope.launch {
        _isLoading.value = true
        try {
            val setNewPass = ForgotPasswordRequest(email, otp, newPass)
            val setNewPassResponse = registerUseCase.setNewPass(setNewPass)
            _setNewPassResult.postValue(setNewPassResponse)
        } catch (e: HttpExceptionUseCase) {
            _error.value = e
        } catch (e: Exception) {
            _error.value = e
        } finally {
            _isLoading.value = false
        }
    }
}