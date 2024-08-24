package com.synrgy7team4.feature_transfer.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.synrgy7team4.common.Resource
import com.synrgy7team4.feature_transfer.data.remote.repository.TransferRepositoryImpl
import com.synrgy7team4.feature_transfer.data.remote.request.AccountRequest
import com.synrgy7team4.feature_transfer.data.remote.request.BalanceRequest
import com.synrgy7team4.feature_transfer.data.remote.request.TransSchedule
import com.synrgy7team4.feature_transfer.data.remote.request.TransferRequest
import com.synrgy7team4.feature_transfer.data.remote.response.account.AccountData
import com.synrgy7team4.feature_transfer.data.remote.response.account.AccountResponse
import com.synrgy7team4.feature_transfer.data.remote.response.balance.BalanceResponse
import com.synrgy7team4.feature_transfer.data.remote.response.mutations.Data
import com.synrgy7team4.feature_transfer.data.remote.response.mutations.MutationsRespomse
import com.synrgy7team4.feature_transfer.data.remote.response.transfer.TransferResponse
import com.synrgy7team4.feature_transfer.data.remote.response.transschedule.TransSchedData
import com.synrgy7team4.feature_transfer.data.remote.response.user.UserResponse
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


class TransferViewModel(private val repository: TransferRepositoryImpl) : ViewModel() {
    private val _userData = MutableLiveData<UserResponse>()
    val userData: LiveData<UserResponse> get() = _userData

    private val _balanceData = MutableLiveData<BalanceResponse>()
    val balanceData: LiveData<BalanceResponse> get() = _balanceData

    private val _transferResponse = MutableLiveData<TransferResponse>()
    val transferResponse: LiveData<TransferResponse> get() = _transferResponse

    private val _scheduleResponse = MutableLiveData<TransSchedData>()
    val scheduleResponse: LiveData<TransSchedData> get() = _scheduleResponse

    private val _mutationData = MutableLiveData<Data>()
    val mutationData: LiveData<Data> get() = _mutationData

    private val _accountList = MutableLiveData<AccountResponse>()
    val accountList: LiveData<AccountResponse> get() = _accountList

    private val _accountSaveResponse = MutableLiveData<AccountResponse>()
    val accountSaveResponse: LiveData<AccountResponse> get() = _accountSaveResponse

    private val _balancePostResponse = MutableLiveData<BalanceResponse>()
    val balancePostResponse: LiveData<BalanceResponse> get() = _balancePostResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private fun handleException(exception: Exception) {
        _errorMessage.postValue(exception.message ?: "An unknown error occurred")
        _isLoading.postValue(false)
    }

    // Function to fetch user data
    fun getUserData(token: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val response = repository.getUserData(token)
                _userData.postValue(response)
                _isLoading.postValue(false)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    // Function to fetch balance
    fun getBalance(token: String, accountNumber: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val response = repository.getBalance(token, accountNumber)
                _balanceData.postValue(response)
                _isLoading.postValue(false)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    // Function to post a transfer
    fun postTransfer(token: String, transferRequest: TransferRequest) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val response = repository.postTransfer(token, transferRequest)
                _transferResponse.postValue(response)
                _isLoading.postValue(false)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    // Function to schedule a transfer
    fun postTransferSched(token: String, transScheduleRequest: TransSchedule) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val response = repository.postTransferSched(token, transScheduleRequest)
                _scheduleResponse.postValue(response)
                _isLoading.postValue(false)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    // Function to fetch mutation data
    fun getMutation(token: String, accountNumber: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val response = repository.getMutation(token, accountNumber)
                _mutationData.postValue(response)
                _isLoading.postValue(false)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    // Function to fetch account list
    fun getAccountList(token: String) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val response = repository.getAccountList(token)
                _accountList.postValue(response)
                _isLoading.postValue(false)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    // Function to save account data
    fun postAccount(token: String, accountRequest: AccountRequest) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val response = repository.postAccount(token, accountRequest)
                _accountSaveResponse.postValue(response)
                _isLoading.postValue(false)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }

    // Function to update balance
    fun postBalance(token: String, balanceRequest: BalanceRequest) {
        _isLoading.postValue(true)
        viewModelScope.launch {
            try {
                val response = repository.postBalance(token, balanceRequest)
                _balancePostResponse.postValue(response)
                _isLoading.postValue(false)
            } catch (e: Exception) {
                handleException(e)
            }
        }
    }
}

//    private val _transferResult = MutableLiveData<Resource<Transfer>>()
//    val transferResult: LiveData<Resource<Transfer>> get() = _transferResult
//
//    private val _balanceResult = MutableLiveData<Resource<Int>>()
//    val balanceResult: LiveData<Resource<Int>> get() = _balanceResult
//
//    private val _accountListResult = MutableLiveData<Resource<List<AccountData>>>()
//    val accountListResult: LiveData<Resource<List<AccountData>>> get() = _accountListResult
//
//    private val _userDataResult = MutableLiveData<Resource<User>>()
//    val userDataResult: LiveData<Resource<User>> get() = _userDataResult
//
//    private val _postBalanceResult = MutableLiveData<Resource<Unit>>()
//    val postBalanceResult: LiveData<Resource<Unit>> get() = _postBalanceResult
//
//    private val _postAccountListResult = MutableLiveData<Resource<Unit>>()
//    val postAccountListResult: LiveData<Resource<Unit>> get() = _postAccountListResult
//
//    private val _error = MutableLiveData<String>()
//    val error: LiveData<String> get() = _error
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> get() = _isLoading
//
//    fun postTransfer(request: TransferReq) {
//        _isLoading.value = true
//        viewModelScope.launch {
//            useCase.postTransfer(request).collect { result ->
//                _transferResult.value = result
//            }
//        }
//    }
//
//    fun getBalance(accountNumber: String) {
//        _balanceResult.value = Resource.Loading()
//        viewModelScope.launch {
//            useCase.getBalance(accountNumber).collect { result ->
//                _balanceResult.value = result
//            }
//        }
//    }
//
//    fun postBalance(request: BalanceReq) {
//        _postBalanceResult.value = Resource.Loading()
//        viewModelScope.launch {
//            useCase.postBalance(request).collect { result ->
//                _postBalanceResult.value = result
//            }
//        }
//    }
//
//    fun getAccountList() {
//        _accountListResult.value = Resource.Loading()
//        viewModelScope.launch {
//            useCase.getAccountList().collect { result ->
//                _accountListResult.value = result
//            }
//        }
//    }
//
//    fun postAccountList(request: AccountReq) {
//        _postAccountListResult.value = Resource.Loading()
//        viewModelScope.launch {
//            useCase.postAccountList(request).collect { result ->
//                _postAccountListResult.value = result
//            }
//        }
//    }
//
//    fun getUserData() {
//        _userDataResult.value = Resource.Loading()
//        viewModelScope.launch {
//            useCase.getUserData().collect { result ->
//                _userDataResult.value = result
//            }
//        }
//    }
//}