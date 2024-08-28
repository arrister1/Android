package com.synrgy7team4.bankingapps.splashScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy7team4.common.TokenHandler
import com.synrgy7team4.common.UserHandler
import kotlinx.coroutines.launch

class SplashViewModel(
    private val tokenHandler: TokenHandler,
    private val userHandler: UserHandler
    ) : ViewModel() {
    private val _isJwtTokenExpired = MutableLiveData<Boolean>()
    val isJwtTokenExpired: LiveData<Boolean> = _isJwtTokenExpired

    fun checkJwtTokenExpire() = viewModelScope.launch {
        if (tokenHandler.isTokenExpired()) {
            tokenHandler.deleteTokens()
            userHandler.deleteUserData()
            _isJwtTokenExpired.value = true
        } else {
            _isJwtTokenExpired.value = false
        }
    }
}