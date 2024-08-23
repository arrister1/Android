package com.synrgy7team4.feature_dashboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy7team4.common.TokenHandler
import com.synrgy7team4.common.UserHandler
import com.synrgy7team4.domain.feature_auth.model.response.UserDataDomain
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AccountViewModel(
    private val tokenHandler: TokenHandler,
    private val userHandler: UserHandler
) : ViewModel() {
    private val _userData = MutableLiveData<UserDataDomain>()
    val userData: LiveData<UserDataDomain> = _userData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<Exception>()
    val error: LiveData<Exception> = _error

    fun getUserData() = viewModelScope.launch {
        _isLoading.value = true
        try {
            val getUserData = UserDataDomain(
                name = userHandler.loadUserName() ?: throw Exception("Nama tidak tersedia"),
                email = userHandler.loadUserEmail() ?: throw Exception("Email tidak tersedia"),
                noHp = userHandler.loadUserPhoneNumber() ?: throw Exception("Nomor hp tidak tersedia"),
                accountNumber = userHandler.loadAccountNumber() ?: throw Exception("Nomor akun tidak tersedia"),
                accountPin = userHandler.loadAccountPin() ?: throw Exception("PIN akun tidak tersedia"),
                dateOfBirth = userHandler.loadDateOfBirth() ?: throw Exception("Tanggal lahir tidak tersedia"),
                noKtp = userHandler.loadKtpNumber() ?: throw Exception("Nomor KTP tidak tersedia"),
                ektpPhoto = userHandler.loadKtpPhoto() ?: throw Exception("Foto KTP tidak tersedia"),
            )
            _userData.value = getUserData
        } catch (e: Exception) {
            _error.value = e
        } finally {
            _isLoading.value = false
        }
    }

    fun deleteTokens(): Deferred<Unit> = viewModelScope.async {
        _isLoading.value = true
        try {
            tokenHandler.deleteTokens()
        } catch (e: Exception) {
            _error.value = e
        } finally {
            _isLoading.value = false
        }
    }

    fun deleteUserData(): Deferred<Unit> = viewModelScope.async {
        _isLoading.value = true
        try {
            userHandler.deleteUserData()
        } catch (e: Exception) {
            _error.value = e
        } finally {
            _isLoading.value = false
        }
    }
}