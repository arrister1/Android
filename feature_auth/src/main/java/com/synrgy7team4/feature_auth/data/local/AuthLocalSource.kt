package com.synrgy7team4.feature_auth.data.remote.local

interface AuthLocalSource {

    suspend fun saveToken(token: String)
    suspend fun loadtoken(): String?
    suspend fun deleteToken()

}