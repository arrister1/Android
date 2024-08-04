package com.synrgy7team4.feature_mutasi.presentation.viewmodel

import androidx.lifecycle.*
import com.synrgy7team4.feature_mutasi.data.response.MutationData
import com.synrgy7team4.feature_mutasi.data.Repository
import kotlinx.coroutines.launch

class MutasiViewmodel(private val repository: Repository) : ViewModel() {

    private val _mutations = MutableLiveData<List<MutationData>>()
    val mutations: LiveData<List<MutationData>> get() = _mutations

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun loadMutations() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val accountNumber = repository.getUserData().body()?.data?.accountNumber.toString()
                val response = repository.getMutations(accountNumber)
                if (response.isSuccessful) {
                    _mutations.value = response.body()?.data
                } else {
                    _error.value = "Error: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Exception: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Dummy data function for testing
    fun loadDummyData() {
        _mutations.value = listOf(
            MutationData(
                id = "1",
                amount = 50000,
                datetime = "2024-08-01T12:00:00Z",
                type = "Transfer",
                status = "Completed",
                description = "Payment for groceries",
                account_from = "123456789",
                account_to = "987654321"
            ),
            MutationData(
                id = "2",
                amount = 20000,
                datetime = "2024-08-02T15:30:00Z",
                type = "Top-up",
                status = "Completed",
                description = "Top-up for mobile",
                account_from = "123456789",
                account_to = "112233445"
            ),
            MutationData(
                id = "3",
                amount = 75000,
                datetime = "2024-08-03T09:15:00Z",
                type = "Transfer",
                status = "Pending",
                description = "Payment for services",
                account_from = "123456789",
                account_to = "998877665"
            )
        )
    }
}
