package com.synrgy7team4.bankingapps.splashScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy7team4.domain.feature_auth.usecase.TokenUseCase
import kotlinx.coroutines.launch

class SplashViewModel(
    private val tokenUseCase: TokenUseCase
) : ViewModel() {
    private val _jwtToken = MutableLiveData<String?>()
    val jwtToken: LiveData<String?> = _jwtToken

    fun getJwtToken() =
        viewModelScope.launch {
            _jwtToken.postValue(tokenUseCase.loadJwtToken())
        }
}