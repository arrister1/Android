package com.synrgy7team4.common

// Interface untuk menangani penggunaan token
interface TokenHandler {
    suspend fun saveTokens(jwtToken: String, refreshToken: String)
    suspend fun loadJwtToken(): String?
    suspend fun loadRefreshToken(): String?
    suspend fun deleteTokens()
    suspend fun isTokenExpired(): Boolean
    suspend fun handlingTokenExpire()
}