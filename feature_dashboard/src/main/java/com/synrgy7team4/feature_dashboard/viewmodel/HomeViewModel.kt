package com.synrgy7team4.feature_dashboard.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy7team4.common.TokenHandler
import com.synrgy7team4.common.UserHandler
import com.synrgy7team4.domain.feature_auth.model.response.UserDataDomain
import com.synrgy7team4.domain.feature_auth.usecase.HttpExceptionUseCase
import com.synrgy7team4.domain.feature_dashboard.usecase.BalanceUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val balanceUseCase: BalanceUseCase,
    private val tokenHandler: TokenHandler,
    private val userHandler: UserHandler
) : ViewModel() {
    private val _userBalance = MutableLiveData<Double>()
    val userBalance: LiveData<Double> = _userBalance

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

    fun getUserBalance() = viewModelScope.launch {
        _isLoading.value = true
        try {
            if (tokenHandler.isTokenExpired()) {
                tokenHandler.handlingTokenExpire()
            } else {
                val jwtToken = tokenHandler.loadJwtToken() ?: throw Exception("JWT token tidak tersedia")
                val getUserBalance = balanceUseCase.getBalance("Bearer $jwtToken", _userData.value?.accountNumber!!).data
                _userBalance.value = getUserBalance
            }
        } catch (e: HttpExceptionUseCase) {
            _error.value = e
        } catch (e: Exception) {
            _error.value = e
        } finally {
            _isLoading.value = false
        }
    }
}