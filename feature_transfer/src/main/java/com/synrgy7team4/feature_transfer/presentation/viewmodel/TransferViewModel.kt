package com.synrgy7team4.feature_transfer.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy7team4.common.Resource
import com.synrgy7team4.feature_transfer.domain.model.Account
import com.synrgy7team4.feature_transfer.domain.model.AccountReq
import com.synrgy7team4.feature_transfer.domain.model.BalanceReq
import com.synrgy7team4.feature_transfer.domain.model.Transfer
import com.synrgy7team4.feature_transfer.domain.model.TransferReq
import com.synrgy7team4.feature_transfer.domain.model.User
import com.synrgy7team4.feature_transfer.domain.usecase.TransferUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class TransferViewModel(private val useCase: TransferUseCase) : ViewModel() {

    private val _transferResult = MutableLiveData<Resource<Transfer>>()
    val transferResult: LiveData<Resource<Transfer>> get() = _transferResult

    private val _balanceResult = MutableLiveData<Resource<Int>>()
    val balanceResult: LiveData<Resource<Int>> get() = _balanceResult

    private val _accountListResult = MutableLiveData<Resource<List<Account>>>()
    val accountListResult: LiveData<Resource<List<Account>>> get() = _accountListResult

    private val _userDataResult = MutableLiveData<Resource<User>>()
    val userDataResult: LiveData<Resource<User>> get() = _userDataResult

    private val _postBalanceResult = MutableLiveData<Resource<Unit>>()
    val postBalanceResult: LiveData<Resource<Unit>> get() = _postBalanceResult

    private val _postAccountListResult = MutableLiveData<Resource<Unit>>()
    val postAccountListResult: LiveData<Resource<Unit>> get() = _postAccountListResult

    fun postTransfer(request: TransferReq) {
        _transferResult.value = Resource.Loading()
        viewModelScope.launch {
            useCase.postTransfer(request).collect { result ->
                _transferResult.value = result
            }
        }
    }

    fun getBalance(accountNumber: String) {
        _balanceResult.value = Resource.Loading()
        viewModelScope.launch {
            useCase.getBalance(accountNumber).collect { result ->
                _balanceResult.value = result
            }
        }
    }

    fun postBalance(request: BalanceReq) {
        _postBalanceResult.value = Resource.Loading()
        viewModelScope.launch {
            useCase.postBalance(request).collect { result ->
                _postBalanceResult.value = result
            }
        }
    }

    fun getAccountList() {
        _accountListResult.value = Resource.Loading()
        viewModelScope.launch {
            useCase.getAccountList().collect { result ->
                _accountListResult.value = result
            }
        }
    }

    fun postAccountList(request: AccountReq) {
        _postAccountListResult.value = Resource.Loading()
        viewModelScope.launch {
            useCase.postAccountList(request).collect { result ->
                _postAccountListResult.value = result
            }
        }
    }

    fun getUserData() {
        _userDataResult.value = Resource.Loading()
        viewModelScope.launch {
            useCase.getUserData().collect { result ->
                _userDataResult.value = result
            }
        }
    }
}