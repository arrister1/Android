package com.synrgy7team4.data.feature_auth.datasource.local

interface AuthLocalDatasource {
    suspend fun saveTokens(jwtToken: String, refreshToken: String)
    suspend fun loadJwtToken(): String?
    suspend fun deleteJwtToken()
}