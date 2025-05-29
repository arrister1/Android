package com.synrgy7team4.common

// Interface untuk menyimpan dan memuat data user
interface UserHandler {
    suspend fun saveUserName(userName: String)
    suspend fun loadUserName(): String?

    suspend fun saveUserEmail(userEmail: String)
    suspend fun loadUserEmail(): String?

    suspend fun saveUserPhoneNumber(userPhoneNumber: String)
    suspend fun loadUserPhoneNumber(): String?

    suspend fun saveAccountNumber(accountNumber: String)
    suspend fun loadAccountNumber(): String?

    suspend fun saveAccountPin(accountPin: String)
    suspend fun loadAccountPin(): String?

    suspend fun saveDateOfBirth(dateOfBirth: String)
    suspend fun loadDateOfBirth(): String?

    suspend fun saveKtpNumber(ktpNumber: String)
    suspend fun loadKtpNumber(): String?

    suspend fun saveKtpPhoto(ktpPhoto: String)
    suspend fun loadKtpPhoto(): String?

    suspend fun deleteUserData()
}